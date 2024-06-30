package com.rental.service;

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
  private final PersonRepository personRepository;
  private final GroupRepository groupRepository;
  private final AccessoryRepository accessoryRepository;
  private final PaymentService paymentService;
  private final ReservationProducer reservationProducer;

  /**
   * Instantiates a new Reservation service.
   *
   * @param reservationRepository the reservation repository
   * @param personRepository      the person repository
   * @param groupRepository       the group repository
   * @param accessoryRepository   the accessory repository
   * @param paymentService        the payment service
   */
  @Autowired
  public ReservationService(ReservationRepository reservationRepository, PersonRepository personRepository, GroupRepository groupRepository, AccessoryRepository accessoryRepository, PaymentService paymentService,
      ReservationProducer reservationProducer) {
    this.reservationRepository = reservationRepository;
    this.personRepository = personRepository;
    this.groupRepository = groupRepository;
    this.accessoryRepository = accessoryRepository;
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
   * @param personId       the person id
   * @param groupId        the group id
   * @param accessoryIds   the accessory ids
   * @param pickupDateTime the pickup date time
   * @param returnDateTime the return date time
   * @param paymentMethod  the payment method
   * @return the reservation dto
   * @throws PersonNotFoundException the person not found exception
   * @throws GroupNotFoundException  the group not found exception
   * @throws StripeException         the stripe exception
   */
  @Transactional
  public ReservationDto createReservation(UUID personId, UUID groupId, List<UUID> accessoryIds, LocalDateTime pickupDateTime, LocalDateTime returnDateTime, String paymentMethod) throws PersonNotFoundException, GroupNotFoundException, StripeException {

    Person person = personRepository.findById(personId).orElseThrow(PersonNotFoundException::new);
    Group group = groupRepository.findById(groupId).orElseThrow(GroupNotFoundException::new);
    List<Accessory> accessories = accessoryRepository.findAllById(accessoryIds);
    int totalDays = calculateTotalDays(pickupDateTime, returnDateTime);
    double calcTotalAmount = calculateTotalAmount(group.getDailyRate(), totalDays, accessories);

    Reservation newReservation = new Reservation();
    newReservation.setPerson(person);
    newReservation.setGroup(group);
    newReservation.setAccessories(accessories);
    newReservation.setPickupDateTime(pickupDateTime);
    newReservation.setReturnDateTime(returnDateTime);
    newReservation.setTotalAmount(calcTotalAmount);
    newReservation.setTotalDays(totalDays);
    newReservation.setReservationStatus(ReservationStatus.PENDING);
    newReservation.setPaymentType(paymentMethod);

    newReservation.setCreatedDate(LocalDateTime.now());

    reservationRepository.save(newReservation);

    return getReservationDto(person, calcTotalAmount, paymentMethod, newReservation);
  }

  private ReservationDto getReservationDto(Person person, Double totalAmount, String paymentMethod, Reservation reservation) throws StripeException {
    if ("online".equalsIgnoreCase(paymentMethod)) {
      Session paymentSession = paymentService.createCheckoutSession(
          totalAmount,
          "http://localhost:8080/payment/success",
          "http://localhost:8080/payment/cancel",
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
    double totalAmount = dailyRate * totalDays;

    if (accessories != null && !accessories.isEmpty()) {
      double accessoriesCost = accessories.stream()
          .mapToDouble(Accessory::getDailyRate)
          .sum();
      totalAmount += accessoriesCost * totalDays;
    }

    return totalAmount;
  }

  private int calculateTotalDays(LocalDateTime pickupDateTime, LocalDateTime returnDateTime) {
    return (int) Duration.between(pickupDateTime, returnDateTime).toDays();
  }
}
