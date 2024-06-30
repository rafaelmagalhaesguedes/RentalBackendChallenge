package com.rental.controller;

import com.rental.controller.dto.reservation.ReservationCreationDto;
import com.rental.controller.dto.reservation.ReservationDto;
import com.rental.controller.dto.reservation.ReservationReadDto;
import com.rental.entity.Reservation;
import com.rental.service.ReservationService;
import com.rental.service.exception.PersonNotFoundException;
import com.rental.service.exception.GroupNotFoundException;
import com.stripe.exception.StripeException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * The type Reservation controller.
 */
@RestController
@RequestMapping("/reservation")
@Validated
public class ReservationController {

  private final ReservationService reservationService;

  /**
   * Instantiates a new Reservation controller.
   *
   * @param reservationService the reservation service
   */
  @Autowired
  public ReservationController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  /**
   * Gets all reservations.
   *
   * @param pageNumber the page number
   * @param pageSize   the page size
   * @return the all reservations
   */
  @GetMapping
  @PreAuthorize("hasAuthority('MANAGER')")
  @Operation(summary = "Get All Reservations", description = "Fetch all reservations with pagination support.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "List of reservations fetched successfully")
  })
  @Cacheable(value = "allReservations", key = "#pageNumber + '-' + #pageSize")
  public List<ReservationReadDto> getAllReservations(
      @RequestParam(required = false, defaultValue = "0") int pageNumber,
      @RequestParam(required = false, defaultValue = "10") int pageSize
  ) {
    List<Reservation> paginatedReservations = reservationService.getAllReservations(pageNumber, pageSize);

    return paginatedReservations.stream()
        .map(ReservationReadDto::fromEntity)
        .toList();
  }

  /**
   * Create reservation reservation dto.
   *
   * @param reservationCreationDto the reservation creation dto
   * @return the reservation dto
   * @throws GroupNotFoundException  the group not found exception
   * @throws PersonNotFoundException the person not found exception
   * @throws StripeException         the stripe exception
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Create Reservation", description = "Create a new reservation.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Reservation created successfully"),
      @ApiResponse(responseCode = "404", description = "Group or Person not found"),
      @ApiResponse(responseCode = "400", description = "Stripe exception occurred")
  })
  public ReservationDto createReservation(@RequestBody @Valid ReservationCreationDto reservationCreationDto) throws GroupNotFoundException, PersonNotFoundException, StripeException {
    return reservationService.createReservation(reservationCreationDto);
  }
}
