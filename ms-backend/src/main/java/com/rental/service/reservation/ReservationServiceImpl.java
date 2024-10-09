package com.rental.service.reservation;

import static com.rental.utils.ReservationUtils.*;

import com.rental.controller.dto.reservation.ReservationRequest;

import com.rental.entity.Accessory;
import com.rental.entity.Reservation;

import com.rental.enums.ReservationStatus;

import com.rental.producer.ReservationProducer;

import com.rental.repository.ReservationRepository;

import com.rental.service.AccessoryService;
import com.rental.service.GroupService;
import com.rental.service.customer.ICustomerService;
import com.rental.service.exception.CustomerNotFoundException;
import com.rental.service.exception.GroupNotFoundException;
import com.rental.service.exception.ReservationNotFoundException;

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
public class ReservationServiceImpl implements IReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationProducer reservationProducer;
    private final AccessoryService accessoryService;
    private final ICustomerService customerService;
    private final GroupService groupService;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository, ReservationProducer reservationProducer, AccessoryService accessoryService, ICustomerService customerService, GroupService groupService) {
        this.reservationRepository = reservationRepository;
        this.reservationProducer = reservationProducer;
        this.accessoryService = accessoryService;
        this.customerService = customerService;
        this.groupService = groupService;
    }

    @Override
    @Transactional
    public Reservation createReservation(ReservationRequest request) throws CustomerNotFoundException, GroupNotFoundException {
        var customer = customerService.getById(request.customerId());
        var group = groupService.getGroupById(request.groupId());
        List<Accessory> accessories = accessoryService.getAccessoriesById(request.accessoryIds());
        var totalDays = calculateTotalDays(request.pickupDateTime(), request.returnDateTime());
        var totalAmount = calculateTotalAmount(group.getDailyRate(), totalDays, accessories);

        var reservation = Reservation.builder()
                .customer(customer)
                .group(group)
                .accessories(accessories)
                .pickupDateTime(request.pickupDateTime())
                .returnDateTime(request.returnDateTime())
                .totalAmount(totalAmount)
                .totalDays(totalDays)
                .reservationStatus(ReservationStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        reservationProducer.publishMessageEmail(reservation);

        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation getReservationById(UUID id) throws ReservationNotFoundException {
        return reservationRepository.findById(id).orElseThrow(ReservationNotFoundException::new);
    }

    @Override
    public List<Reservation> getAllReservations(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Reservation> page = reservationRepository.findAll(pageable);
        return page.toList();
    }
}
