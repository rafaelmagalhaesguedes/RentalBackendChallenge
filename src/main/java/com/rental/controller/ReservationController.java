package com.rental.controller;

import com.rental.controller.dto.reservation.ReservationCreationDto;
import com.rental.controller.dto.reservation.ReservationDto;
import com.rental.service.ReservationService;
import com.rental.service.exception.CustomerNotFoundException;
import com.rental.service.exception.GroupNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservation")
@Validated
public class ReservationController {

  private final ReservationService reservationService;
  @Autowired
  public ReservationController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ReservationDto createReservation(@RequestBody @Valid ReservationCreationDto reservationCreationDto)
      throws GroupNotFoundException, CustomerNotFoundException {
    return ReservationDto.fromEntity(reservationService.createReservation(
        reservationCreationDto.customerId(), reservationCreationDto.groupId(),
        reservationCreationDto.accessoryIds(), reservationCreationDto.pickupDateTime(),
        reservationCreationDto.returnDateTime(), reservationCreationDto.totalAmount(),
        reservationCreationDto.status(), reservationCreationDto.paymentMethod()
    ));
  }

}
