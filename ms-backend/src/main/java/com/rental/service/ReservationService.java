package com.rental.service;

import com.rental.controller.dto.reservation.ReservationDto;
import com.rental.entity.Accessory;
import com.rental.entity.Person;
import com.rental.entity.Reservation;
import com.rental.entity.Group;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

  private final ReservationRepository reservationRepository;
  private final PersonRepository personRepository;
  private final GroupRepository groupRepository;
  private final AccessoryRepository accessoryRepository;
  private final PaymentService paymentService;

  @Autowired
  public ReservationService(ReservationRepository reservationRepository, PersonRepository personRepository, GroupRepository groupRepository, AccessoryRepository accessoryRepository, PaymentService paymentService) {
    this.reservationRepository = reservationRepository;
    this.personRepository = personRepository;
    this.groupRepository = groupRepository;
    this.accessoryRepository = accessoryRepository;
    this.paymentService = paymentService;
  }

  public Reservation getReservationById(UUID id) throws ReservationNotFoundException {
    return reservationRepository.findById(id)
        .orElseThrow(ReservationNotFoundException::new);
  }

  public List<Reservation> getAllReservations(int pageNumber, int pageSize) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    Page<Reservation> page = reservationRepository.findAll(pageable);

    return page.toList();
  }

  @Transactional
  public ReservationDto createReservation(UUID personId, UUID groupId, List<UUID> accessoryIds, LocalDateTime pickupDateTime, LocalDateTime returnDateTime, Double totalAmount, String status, String paymentMethod) throws PersonNotFoundException, GroupNotFoundException, StripeException {

    Reservation reservation = getReservation(personId, groupId, accessoryIds, pickupDateTime, returnDateTime, totalAmount, status, paymentMethod);

    return getReservationDto(totalAmount, paymentMethod, reservation);
  }

  private ReservationDto getReservationDto(Double totalAmount, String paymentMethod, Reservation reservation) throws StripeException {
    if ("online".equalsIgnoreCase(paymentMethod)) {
      Session paymentSession = paymentService.createCheckoutSession(
          totalAmount,
          "http://localhost:8080/success",
          "http://localhost:8080/cancel",
          reservation
      );
      return ReservationDto.fromEntity(reservation, paymentSession.getUrl());
    } else {
      return ReservationDto.fromEntity(reservation, null);
    }
  }

  private Reservation getReservation(UUID personId, UUID groupId, List<UUID> accessoryIds, LocalDateTime pickupDateTime, LocalDateTime returnDateTime, Double totalAmount, String status, String paymentMethod) throws PersonNotFoundException, GroupNotFoundException {
    Person person = personRepository.findById(personId).orElseThrow(PersonNotFoundException::new);
    Group group = groupRepository.findById(groupId).orElseThrow(GroupNotFoundException::new);
    List<Accessory> accessories = accessoryRepository.findAllById(accessoryIds);

    return createNewReservation(person, group, accessories, pickupDateTime, returnDateTime, totalAmount, status, paymentMethod);
  }

  private Reservation createNewReservation(Person person, Group group, List<Accessory> accessories, LocalDateTime pickupDateTime, LocalDateTime returnDateTime, Double totalAmount, String status, String paymentMethod) {
    Reservation newReservation = new Reservation();
    newReservation.setPerson(person);
    newReservation.setGroup(group);
    newReservation.setAccessories(accessories);
    newReservation.setPickupDateTime(pickupDateTime);
    newReservation.setReturnDateTime(returnDateTime);
    newReservation.setTotalAmount(totalAmount);
    newReservation.setStatus(status);
    newReservation.setPaymentMethod(paymentMethod);

    return reservationRepository.save(newReservation);
  }
}
