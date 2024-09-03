package com.rental.controller.dto.reservation;

import com.rental.entity.Reservation;
import com.rental.entity.Group;
import com.rental.entity.Accessory;
import com.rental.repository.GroupRepository;
import com.rental.repository.AccessoryRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Reservation creation dto.
 */
public record ReservationCreationDto(
        String fullName,
        String email,
        String document,
        UUID groupId,
        List<UUID> accessoryIds,
        LocalDateTime pickupDateTime,
        LocalDateTime returnDateTime
) {
}