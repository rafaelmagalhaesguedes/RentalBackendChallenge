package com.rental.service;

import com.rental.controller.dto.reservation.ReservationCreationDto;
import com.rental.controller.dto.reservation.ReservationDto;
import com.rental.controller.dto.reservation.ReservationPaymentDto;
import com.rental.entity.Accessory;
import com.rental.entity.Person;
import com.rental.entity.Reservation;
import com.rental.entity.Group;
import com.rental.enums.PaymentType;
import com.rental.enums.ReservationStatus;
import com.rental.producer.ReservationProducer;
import com.rental.repository.ReservationRepository;
import com.rental.service.exception.PersonNotFoundException;
import com.rental.service.exception.GroupNotFoundException;
import com.rental.service.exception.ReservationNotFoundException;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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

  private static final String PAYMENT_SUCCESS_URL = "http://localhost:5173/success";
  private static final String PAYMENT_CANCEL_URL = "http://localhost:5173/cancel";

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
  public ReservationService(
          ReservationRepository reservationRepository, PersonService personService,
          GroupService groupService, AccessoryService accessoryService,
          PaymentService paymentService, ReservationProducer reservationProducer
  ) {
    this.reservationRepository = reservationRepository;
    this.personService = personService;
    this.groupService = groupService;
    this.accessoryService = accessoryService;
    this.paymentService = paymentService;
    this.reservationProducer = reservationProducer;
  }

  /**
   * Create reservation with online payment
   *
   * @param reservationDto the reservation dto
   * @return the reservation payment dto
   * @throws PersonNotFoundException the person not found exception
   * @throws GroupNotFoundException  the group not found exception
   * @throws StripeException         the stripe exception
   */
  @Transactional
  public ReservationPaymentDto createReservationWithOnlinePayment(ReservationCreationDto reservationDto) throws PersonNotFoundException, GroupNotFoundException, StripeException {
    Reservation reservation = createReservation(reservationDto, true);
    return processOnlinePayment(reservation);
  }

  /**
   * Create reservation with payment in store
   *
   * @param reservationDto the reservation dto
   * @return the reservation dto
   * @throws PersonNotFoundException the person not found exception
   * @throws GroupNotFoundException  the group not found exception
   */
  @Transactional
  public ReservationDto createReservationWithStorePayment(ReservationCreationDto reservationDto) throws PersonNotFoundException, GroupNotFoundException {
    Reservation reservation = createReservation(reservationDto, false);
    return processStorePayment(reservation);
  }

  /**
   * Creates a reservation based on the provided reservation data.
   *
   * @param reservationDto the data transfer object containing reservation details
   * @return the created reservation
   * @throws PersonNotFoundException if the person with the given ID is not found
   * @throws GroupNotFoundException if the group with the given ID is not found
   */
  private Reservation createReservation(ReservationCreationDto reservationDto, boolean reservationType) throws PersonNotFoundException, GroupNotFoundException {
    Person person = personService.getPersonById(reservationDto.personId());
    Group group = groupService.getGroupById(reservationDto.groupId());
    List<Accessory> accessories = accessoryService.getAccessoriesById(reservationDto.accessoryIds());

    return buildAndSaveReservation(reservationDto, person, group, accessories, reservationType);
  }

  /**
   * Builds and saves a reservation.
   *
   * @param reservationDto the data transfer object containing reservation details
   * @param person the person making the reservation
   * @param group the group being reserved
   * @param accessories the list of accessories
   * @return the saved reservation
   */
  private Reservation buildAndSaveReservation(ReservationCreationDto reservationDto, Person person, Group group, List<Accessory> accessories, boolean reservationType) {
    Reservation reservation = new Reservation();

    reservation.setPerson(person);
    reservation.setGroup(group);
    reservation.setAccessories(accessories);
    reservation.setPickupDateTime(reservationDto.pickupDateTime());
    reservation.setReturnDateTime(reservationDto.returnDateTime());
    reservation.setTotalAmount(reservationDto.totalAmount());
    reservation.setTotalDays(reservationDto.totalDays());
    reservation.setReservationStatus(ReservationStatus.PENDING);

    if (reservationType)
      reservation.setPaymentType(String.valueOf(PaymentType.ONLINE_PAYMENT));
    else
      reservation.setPaymentType(String.valueOf(PaymentType.STORE_PAYMENT));

    reservation.setCreatedDate(LocalDateTime.now());

    return reservationRepository.save(reservation);
  }

  /**
   * Processes an online payment for a reservation.
   *
   * @param reservation the reservation to be paid for
   * @return the reservation payment data transfer object
   * @throws StripeException if an error occurs while processing the payment
   */
  private ReservationPaymentDto processOnlinePayment(Reservation reservation) throws StripeException {
    Session paymentSession = paymentService.createCheckoutSession(
            reservation.getTotalAmount(), PAYMENT_SUCCESS_URL, PAYMENT_CANCEL_URL, reservation);

    return ReservationPaymentDto.fromEntity(reservation, paymentSession.getUrl());
  }

  /**
   * Processes a store payment for a reservation.
   *
   * @param reservation the reservation to be paid for
   * @return the reservation data transfer object
   */
  private ReservationDto processStorePayment(Reservation reservation) {
    reservationProducer.publishMessageEmail(reservation.getPerson(), reservation);
    return ReservationDto.fromEntity(reservation);
  }

  /**
   * Gets reservation by id.
   *
   * @param id the id
   * @return the reservation by id
   * @throws ReservationNotFoundException the reservation not found exception
   */
  public Reservation getReservationById(UUID id) throws ReservationNotFoundException {
    return reservationRepository.findById(id).orElseThrow(ReservationNotFoundException::new);
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
