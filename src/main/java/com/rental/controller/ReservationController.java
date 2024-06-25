package com.rental.controller;

import com.rental.controller.dto.reservation.ReservationCreationDto;
import com.rental.controller.dto.reservation.ReservationDto;
import com.rental.controller.dto.reservation.ReservationReadDto;
import com.rental.entity.Reservation;
import com.rental.service.ReservationService;
import com.rental.service.exception.CustomerNotFoundException;
import com.rental.service.exception.GroupNotFoundException;
import com.stripe.exception.StripeException;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservation")
@Validated
public class ReservationController {

  private final ReservationService reservationService;

  @Autowired
  public ReservationController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @GetMapping
  @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
  public List<ReservationReadDto> getAllReservations(
      @RequestParam(required = false, defaultValue = "0") int pageNumber,
      @RequestParam(required = false, defaultValue = "10") int pageSize
  ) {
    List<Reservation> paginatedReservations = reservationService.getAllReservations(pageNumber, pageSize);

    return paginatedReservations.stream()
        .map(ReservationReadDto::fromEntity)
        .toList();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAuthority('USER')")
  public ReservationDto createReservation(@RequestBody @Valid ReservationCreationDto reservationCreationDto)
      throws GroupNotFoundException, CustomerNotFoundException, StripeException {
    return reservationService.createReservation(
        reservationCreationDto.customerId(), reservationCreationDto.groupId(),
        reservationCreationDto.accessoryIds(), reservationCreationDto.pickupDateTime(),
        reservationCreationDto.returnDateTime(), reservationCreationDto.totalAmount(),
        reservationCreationDto.status(), reservationCreationDto.paymentMethod()
    );
  }
}
