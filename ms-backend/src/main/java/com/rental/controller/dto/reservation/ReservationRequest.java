package com.rental.controller.dto.reservation;

import com.rental.entity.Accessory;
import com.rental.entity.Customer;
import com.rental.entity.Group;
import com.rental.entity.Reservation;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Reservation creation dto.
 */
public record ReservationRequest(
        @NotNull(message = "Customer ID is mandatory")
        UUID customerId,

        @NotNull(message = "Group ID is mandatory")
        UUID groupId,

        @NotNull(message = "Accessory IDs are mandatory")
        @Size(min = 1, message = "There must be at least one accessory ID")
        List<UUID> accessoryIds,

        @NotNull(message = "Pickup date and time is mandatory")
        @FutureOrPresent(message = "Pickup date and time must be in the future or present")
        LocalDateTime pickupDateTime,

        @NotNull(message = "Return date and time is mandatory")
        @FutureOrPresent(message = "Return date and time must be in the future or present")
        LocalDateTime returnDateTime
) {
    public Reservation toEntity() {
        var customer = new Customer();
        customer.setId(customerId);

        var group = new Group();
        group.setId(groupId);

        List<Accessory> accessories = accessoryIds.stream()
                .map(id -> {
                    var accessory = new Accessory();
                    accessory.setId(id);
                    return accessory;
                })
                .toList();

        return Reservation.builder()
                .customer(customer)
                .group(group)
                .accessories(accessories)
                .pickupDateTime(pickupDateTime)
                .returnDateTime(returnDateTime)
                .build();
    }
}