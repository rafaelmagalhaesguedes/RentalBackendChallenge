package com.rental.service;

import com.rental.controller.dto.reservation.ReservationCreationDto;
import com.rental.controller.dto.reservation.ReservationDto;
import com.rental.entity.Accessory;
import com.rental.entity.Person;
import com.rental.entity.Reservation;
import com.rental.entity.Group;
import com.rental.enums.ReservationStatus;
import com.rental.producer.ReservationProducer;
import com.rental.repository.AccessoryRepository;
import com.rental.repository.PersonRepository;
import com.rental.repository.GroupRepository;
import com.rental.repository.ReservationRepository;
import com.rental.service.exception.PersonNotFoundException;
import com.rental.service.exception.GroupNotFoundException;
import com.rental.service.exception.ReservationNotFoundException;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import jakarta.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * The type Reservation service.
 */
@Service
public class ReservationService {

  private final ReservationRepository reservationRepository;
  private final PersonService personService;
  private final GroupService groupService;
  private final AccessoryService accessoryService;
  private final PaymentService paymentService;
  private final ReservationProducer reservationProducer;

  private static final String PAYMENT_SUCCESS_URL = "http://localhost:8080/payment/success";
  private static final String PAYMENT_CANCEL_URL = "http://localhost:8080/payment/cancel";

  /**
   * Instantiates a new Reservation service.
   *
   * @param reservationRepository the reservation repository
   * @param personService         the person service
   * @param groupService          the group service
   * @param accessoryService      the accessory service
   * @param paymentService        the payment service
   * @param reservationProducer   the reservation producer
   */
  @Autowired
  public ReservationService(ReservationRepository reservationRepository, PersonService personService, GroupService groupService, AccessoryService accessoryService, PaymentService paymentService, ReservationProducer reservationProducer) {
    this.reservationRepository = reservationRepository;
    this.personService = personService;
    this.groupService = groupService;
    this.accessoryService = accessoryService;
    this.paymentService = paymentService;
    this.reservationProducer = reservationProducer;
  }

  /**
   * Creates a new reservation based on the provided ReservationCreationDto.
   *
   * @param reservationDto The ReservationCreationDto containing reservation details.
   * @return The ReservationDto representing the created reservation.
   * @throws PersonNotFoundException If the person with the specified ID is not found.
   * @throws GroupNotFoundException If the vehicle group with the specified ID is not found.
   * @throws StripeException If there is an issue with the Stripe API during payment processing.
   */
  @Transactional
  public ReservationDto createReservation(ReservationCreationDto reservationDto) throws PersonNotFoundException, GroupNotFoundException, StripeException {
    Person person = personService.getPersonById(reservationDto.personId());
    Group group = groupService.getGroupById(reservationDto.groupId());
    List<Accessory> accessories = accessoryService.getAccessoriesById(reservationDto.accessoryIds());

    int totalDays = getTotalDays(reservationDto.pickupDateTime(), reservationDto.returnDateTime());
    double calcTotalAmount = getTotalAmount(group.getDailyRate(), totalDays, accessories);

    Reservation newReservation = buildNewReservation(reservationDto, person, group, accessories, calcTotalAmount, totalDays);

    return reserveCreated(person, calcTotalAmount, reservationDto.paymentType(), newReservation);
  }

  /**
   * Builds a new reservation based on the provided data.
   *
   * @param reservationDto    The DTO containing reservation data.
   * @param person            The person making the reservation.
   * @param group             The group of vehicles for the reservation.
   * @param accessories       The list of accessories selected for the reservation.
   * @param calcTotalAmount   The calculated total amount for the reservation.
   * @param totalDays         The total number of days for the reservation.
   * @return                  The newly created Reservation entity.
   */
  private Reservation buildNewReservation(ReservationCreationDto reservationDto, Person person, Group group, List<Accessory> accessories, double calcTotalAmount, int totalDays) {
    Reservation newReservation = new Reservation();

    // Set properties
    newReservation.setPerson(person);
    newReservation.setGroup(group);
    newReservation.setAccessories(accessories);
    newReservation.setPickupDateTime(reservationDto.pickupDateTime());
    newReservation.setReturnDateTime(reservationDto.returnDateTime());
    newReservation.setTotalAmount(calcTotalAmount);
    newReservation.setTotalDays(totalDays);
    newReservation.setReservationStatus(ReservationStatus.PENDING);
    newReservation.setPaymentType(reservationDto.paymentType());
    newReservation.setCreatedDate(LocalDateTime.now());

    return reservationRepository.save(newReservation);
  }

  /**
   * Processes the payment for a reservation and publishes an email notification if payment is successful.
   *
   * @param person            The person associated with the reservation.
   * @param totalAmount       The total amount to be paid for the reservation.
   * @param paymentMethod     The payment method (online or other).
   * @param reservation       The reservation entity.
   * @return                  The ReservationDto representing the reservation after payment processing.
   * @throws StripeException  If there is an issue with the Stripe API during payment processing.
   */
  private ReservationDto reserveCreated(Person person, Double totalAmount, String paymentMethod, Reservation reservation) throws StripeException {
    if ("online".equalsIgnoreCase(paymentMethod)) {  // Online payment
      Session paymentSession = paymentService.createCheckoutSession(
          totalAmount,
          PAYMENT_SUCCESS_URL,
          PAYMENT_CANCEL_URL,
          reservation
      );

      if (paymentSession.getUrl().contains("success")) {
        reservationProducer.publishMessageEmail(person, reservation);
      }

      return ReservationDto.fromEntity(reservation, paymentSession.getUrl());
    } else {  // Offline payment
      reservationProducer.publishMessageEmail(person, reservation);
      return ReservationDto.fromEntity(reservation, null);
    }
  }

  /**
   * Calculates the total amount for a reservation including daily rates of vehicles and accessories.
   *
   * @param dailyRate         The daily rate of the vehicle group.
   * @param totalDays         The total number of days for the reservation.
   * @param accessories       The list of accessories selected for the reservation.
   * @return                  The calculated total amount for the reservation.
   */
  private double getTotalAmount(double dailyRate, int totalDays, List<Accessory> accessories) {
    double accessoriesCost = accessories.stream()
        .mapToDouble(Accessory::getDailyRate)
        .sum();
    return dailyRate * totalDays + accessoriesCost * totalDays;
  }

  /**
   * Calculates the total number of days between the pickup and return date times.
   *
   * @param pickupDateTime    The date and time of pickup for the reservation.
   * @param returnDateTime    The date and time of return for the reservation.
   * @return                  The total number of days between pickup and return dates.
   */
  private int getTotalDays(LocalDateTime pickupDateTime, LocalDateTime returnDateTime) {
    return (int) Duration.between(pickupDateTime, returnDateTime).toDays();
  }

  /**
   * Gets reservation by id.
   *
   * @param id the id
   * @return the reservation by id
   * @throws ReservationNotFoundException the reservation not found exception
   */
  public Reservation getReservationById(UUID id) throws ReservationNotFoundException {
    return reservationRepository.findById(id)
        .orElseThrow(ReservationNotFoundException::new);
  }

  /**
   * Gets all reservations.
   *
   * @param pageNumber the page number
   * @param pageSize   the page size
   * @return the all reservations
   */
  public List<Reservation> getAllReservations(int pageNumber, int pageSize) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    Page<Reservation> page = reservationRepository.findAll(pageable);

    return page.toList();
  }
}
