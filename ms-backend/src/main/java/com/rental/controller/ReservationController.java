package com.rental.controller;

import com.rental.controller.dto.reservation.ReservationCreationDto;
import com.rental.controller.dto.reservation.ReservationDto;
import com.rental.service.ReservationService;
import com.rental.service.exception.PersonNotFoundException;
import com.rental.service.exception.GroupNotFoundException;
import com.rental.service.exception.ReservationNotFoundException;
import com.stripe.exception.StripeException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
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

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Create Store Reservation", description = "Create a new reservation with in-store payment.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Reservation created successfully"),
          @ApiResponse(responseCode = "404", description = "Group or Person not found")
  })
  public ReservationDto createStoreReservation(@RequestBody @Valid ReservationCreationDto reservationCreationDto) throws GroupNotFoundException, PersonNotFoundException {
    return ReservationDto.fromEntity(
            reservationService.createReservation(reservationCreationDto)
    );
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get Reservation By ID", description = "Fetch a reservation by its ID.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Reservation fetched successfully"),
          @ApiResponse(responseCode = "404", description = "Reservation not found")
  })
  public ReservationDto getReservationById(@PathVariable UUID id) throws ReservationNotFoundException {
    return ReservationDto.fromEntity(reservationService.getReservationById(id));
  }

  @GetMapping
  @Operation(summary = "Get All Reservations", description = "Fetch all reservations with pagination support.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "List of reservations fetched successfully")
  })
  @Cacheable(value = "allReservations", key = "#pageNumber + '-' + #pageSize")
  public List<ReservationDto> getAllReservations(
          @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
    return reservationService.getAllReservations(pageNumber, pageSize)
            .stream()
            .map(ReservationDto::fromEntity)
            .toList();
  }
}
