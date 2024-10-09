package com.rental.controller;

import com.rental.controller.dto.reservation.ReservationRequest;
import com.rental.controller.dto.reservation.ReservationResponse;

import com.rental.service.exception.CustomerNotFoundException;
import com.rental.service.exception.GroupNotFoundException;
import com.rental.service.exception.ReservationNotFoundException;
import com.rental.service.reservation.IReservationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

  private final IReservationService reservationService;

  @Autowired
  public ReservationController(IReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Create Reservation", description = "Create a new reservation with in-store payment.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Reservation created successfully"),
          @ApiResponse(responseCode = "404", description = "Group or Person not found") })
  public ReservationResponse createReservation(
          @RequestBody @Valid ReservationRequest request
  ) throws CustomerNotFoundException, GroupNotFoundException {
    var newReservation = reservationService.createReservation(request);

    return ReservationResponse.fromEntity(newReservation);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get Reservation By ID", description = "Fetch a reservation by its ID.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Reservation fetched successfully"),
          @ApiResponse(responseCode = "404", description = "Reservation not found") })
  public ReservationResponse getReservationById(@PathVariable UUID id) throws ReservationNotFoundException {
    return ReservationResponse.fromEntity(reservationService.getReservationById(id));
  }

  @GetMapping
  @Cacheable(value = "allReservations", key = "#pageNumber + '-' + #pageSize")
  @Operation(summary = "Get All Reservations", description = "Fetch all reservations with pagination support.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "List of reservations fetched successfully") })
  public List<ReservationResponse> getAllReservations(
          @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize
  ) {
    var list = reservationService.getAllReservations(pageNumber, pageSize);

    return list.stream()
            .map(ReservationResponse::fromEntity)
            .toList();
  }
}
