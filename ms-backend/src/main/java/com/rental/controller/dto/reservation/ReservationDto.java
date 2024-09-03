package com.rental.controller.dto.reservation;

import com.rental.controller.dto.accessory.AccessoryDto;

import com.rental.entity.Group;
import com.rental.entity.Reservation;

import com.rental.enums.ReservationStatus;

import java.time.LocalDateTime;

import java.util.List;
import java.util.UUID;

public record ReservationDto(
    UUID id,
    String fullName,
    String email,
    Group group,
    List<AccessoryDto> accessories,
    LocalDateTime pickupDateTime,
    LocalDateTime returnDateTime,
    Double totalAmount,
    Integer totalDays,
    ReservationStatus reservationStatus,
    LocalDateTime createdDate
) {
  public static ReservationDto fromEntity(Reservation reservation) {
    return new ReservationDto(
        reservation.getId(),
        reservation.getFullName(),
        reservation.getEmail(),
        reservation.getGroup(),
        reservation.getAccessories()
            .stream()
            .map(AccessoryDto::fromEntity)
            .toList(),
        reservation.getPickupDateTime(),
        reservation.getReturnDateTime(),
        reservation.getTotalAmount(),
        reservation.getTotalDays(),
        reservation.getReservationStatus(),
        reservation.getCreatedDate()
    );
  }
}

