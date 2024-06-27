package com.rental.controller.dto.reservation;

import com.rental.entity.Reservation;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * The type Reservation creation dto.
 */
public record ReservationCreationDto(
    UUID personId,
    UUID groupId,
    List<UUID> accessoryIds,
    LocalDateTime pickupDateTime,
    LocalDateTime returnDateTime,
    Double totalAmount,
    String paymentMethod
) { }

