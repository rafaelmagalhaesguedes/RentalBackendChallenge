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
   * @param personService       the person service
   * @param groupService        the group service
   * @param accessoryService    the accessory service
   * @param paymentService        the payment service
   * @param reservationProducer        the reservation producer
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

  /**
   * Create reservation reservation dto.
   *
   * @param reservationDto the reservationDto
   * @return the reservation dto
   * @throws PersonNotFoundException the person not found exception
   * @throws GroupNotFoundException  the group not found exception
   * @throws StripeException         the stripe exception
   */
  @Transactional
  public ReservationDto createReservation(ReservationCreationDto reservationDto) throws PersonNotFoundException, GroupNotFoundException, StripeException {

    Person person = personService.getPersonById(reservationDto.personId());

    Group group = groupService.getGroupById(reservationDto.groupId());

    List<Accessory> accessories = accessoryService.getAccessoriesById(reservationDto.accessoryIds());

    int totalDays = calculateTotalDays(reservationDto.pickupDateTime(), reservationDto.returnDateTime());
    double calcTotalAmount = calculateTotalAmount(group.getDailyRate(), totalDays, accessories);

    Reservation newReservation = buildNewReservation(reservationDto, person, group, accessories, calcTotalAmount, totalDays);

    return processReservationPayment(person, calcTotalAmount, reservationDto.paymentType(), newReservation);
  }

  private Reservation buildNewReservation(ReservationCreationDto reservationDto, Person person, Group group, List<Accessory> accessories, double calcTotalAmount, int totalDays) {
    Reservation newReservation = new Reservation();
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

  private ReservationDto processReservationPayment(Person person, Double totalAmount, String paymentMethod, Reservation reservation) throws StripeException {
    if ("online".equalsIgnoreCase(paymentMethod)) {
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
    } else {
      reservationProducer.publishMessageEmail(person, reservation);
      return ReservationDto.fromEntity(reservation, null);
    }
  }

  private double calculateTotalAmount(double dailyRate, int totalDays, List<Accessory> accessories) {
    double accessoriesCost = accessories.stream()
        .mapToDouble(Accessory::getDailyRate)
        .sum();
    return dailyRate * totalDays + accessoriesCost * totalDays;
  }

  private int calculateTotalDays(LocalDateTime pickupDateTime, LocalDateTime returnDateTime) {
    return (int) Duration.between(pickupDateTime, returnDateTime).toDays();
  }
}
