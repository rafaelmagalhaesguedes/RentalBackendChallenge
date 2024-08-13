package com.rental.controller.dto.reservation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Reservation creation dto.
 */
public record ReservationCreationDto(
    UUID personId,
    UUID groupId,
    List<UUID> accessoryIds,
    LocalDateTime pickupDateTime,
    LocalDateTime returnDateTime,
    Double totalAmount,
    Integer totalDays
) { }