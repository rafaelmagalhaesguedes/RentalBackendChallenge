package com.rental.service;

import com.rental.entity.Accessory;
import com.rental.entity.Customer;
import com.rental.entity.Reservation;
import com.rental.entity.Group;
import com.rental.repository.AccessoryRepository;
import com.rental.repository.CustomerRepository;
import com.rental.repository.GroupRepository;
import com.rental.repository.ReservationRepository;
import com.rental.service.exception.CustomerNotFoundException;
import com.rental.service.exception.GroupNotFoundException;
import com.rental.service.exception.ReservationNotFoundException;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

  private final ReservationRepository reservationRepository;
  private final CustomerRepository customerRepository;
  private final GroupRepository groupRepository;
  private final AccessoryRepository accessoryRepository;

  @Autowired
  public ReservationService(ReservationRepository reservationRepository,
      CustomerRepository customerRepository, GroupRepository groupRepository,
      AccessoryRepository accessoryRepository) {
    this.reservationRepository = reservationRepository;
    this.customerRepository = customerRepository;
    this.groupRepository = groupRepository;
    this.accessoryRepository = accessoryRepository;
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
  public Reservation createReservation(
      UUID customerId, UUID groupId, List<UUID> accessoryIds,
      LocalDateTime pickupDateTime, LocalDateTime returnDateTime,
      Double totalAmount, String status, String paymentMethod
  ) throws CustomerNotFoundException, GroupNotFoundException {

    // Get Customer
    Customer customer = customerRepository.findById(customerId)
        .orElseThrow(CustomerNotFoundException::new);

    // Get Group
    Group group = groupRepository.findById(groupId)
        .orElseThrow(GroupNotFoundException::new);

    // Get Accessory
    List<Accessory> accessories = accessoryRepository.findAllById(accessoryIds);

    //  Create and return new reservation
    return createNewReservation(customer, group, accessories, pickupDateTime, returnDateTime,
        totalAmount, status, paymentMethod);
  }

  private Reservation createNewReservation(
      Customer customer, Group group, List<Accessory> accessories,
      LocalDateTime pickupDateTime, LocalDateTime returnDateTime,
      Double totalAmount, String status, String paymentMethod
  ) {
    Reservation newReservation = new Reservation();
    newReservation.setCustomer(customer);
    newReservation.setGroup(group);
    newReservation.setAccessories(accessories);
    newReservation.setPickupDateTime(pickupDateTime);
    newReservation.setReturnDateTime(returnDateTime);
    newReservation.setTotalAmount(totalAmount);
    newReservation.setStatus(status);
    newReservation.setPaymentMethod(paymentMethod);

    return newReservation;
  }
}

