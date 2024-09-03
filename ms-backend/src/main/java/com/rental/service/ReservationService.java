package com.rental.service;

import com.rental.controller.dto.reservation.ReservationCreationDto;
import com.rental.controller.dto.reservation.ReservationDto;
import com.rental.entity.Accessory;
import com.rental.entity.Person;
import com.rental.entity.Reservation;
import com.rental.entity.Group;
import com.rental.enums.ReservationStatus;
import com.rental.producer.ReservationProducer;
import com.rental.repository.ReservationRepository;
import com.rental.service.exception.PersonNotFoundException;
import com.rental.service.exception.GroupNotFoundException;
import com.rental.service.exception.ReservationNotFoundException;
import com.rental.utils.ReservationUtils;
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

@Service
public class ReservationService {

  private final ReservationRepository reservationRepository;
  private final GroupService groupService;
  private final AccessoryService accessoryService;
  private final ReservationProducer reservationProducer;

  @Autowired
  public ReservationService(ReservationRepository reservationRepository, GroupService groupService, AccessoryService accessoryService, ReservationProducer reservationProducer) {
    this.reservationRepository = reservationRepository;
    this.groupService = groupService;
    this.accessoryService = accessoryService;
    this.reservationProducer = reservationProducer;
  }

  @Transactional
  public Reservation createReservation(ReservationCreationDto reservationDto) throws GroupNotFoundException {
    Group group = groupService.getGroupById(reservationDto.groupId());
    List<Accessory> accessories = accessoryService.getAccessoriesById(reservationDto.accessoryIds());
    var totalDays = ReservationUtils.calculateTotalDays(reservationDto.pickupDateTime(), reservationDto.returnDateTime());
    var totalAmount = ReservationUtils.calculateTotalAmount(group.getDailyRate(), totalDays, accessories);

    Reservation reservation = new Reservation();

    reservation.setGroup(group);
    reservation.setAccessories(accessories);
    reservation.setPickupDateTime(reservationDto.pickupDateTime());
    reservation.setReturnDateTime(reservationDto.returnDateTime());
    reservation.setTotalAmount(totalAmount);
    reservation.setTotalDays(totalDays);
    reservation.setReservationStatus(ReservationStatus.PENDING);
    reservation.setCreatedDate(LocalDateTime.now());

    reservationProducer.publishMessageEmail(reservation);

    return reservationRepository.save(reservation);
  }

  public Reservation getReservationById(UUID id) throws ReservationNotFoundException {
    return reservationRepository.findById(id).orElseThrow(ReservationNotFoundException::new);
  }

  public List<Reservation> getAllReservations(int pageNumber, int pageSize) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    Page<Reservation> page = reservationRepository.findAll(pageable);
    return page.toList();
  }
}
