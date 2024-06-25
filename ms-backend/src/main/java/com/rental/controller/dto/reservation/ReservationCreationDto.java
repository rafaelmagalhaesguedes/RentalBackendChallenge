package com.rental.controller.dto.reservation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ReservationCreationDto(
    UUID personId,
    UUID groupId,
    List<UUID> accessoryIds,
    LocalDateTime pickupDateTime,
    LocalDateTime returnDateTime,
    Double totalAmount,
    String status,
    String paymentMethod
) { }

