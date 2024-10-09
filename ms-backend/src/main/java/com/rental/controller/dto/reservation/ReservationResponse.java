package com.rental.controller.dto.reservation;

import com.rental.controller.dto.accessory.AccessoryDto;

import com.rental.entity.Customer;
import com.rental.entity.Group;
import com.rental.entity.Reservation;

import com.rental.enums.PaymentType;
import com.rental.enums.ReservationStatus;

import java.time.LocalDateTime;

import java.util.List;
import java.util.UUID;

public record ReservationResponse(
    UUID id,
    Customer customer,
    Group group,
    List<AccessoryDto> accessories,
    LocalDateTime pickupDateTime,
    LocalDateTime returnDateTime,
    PaymentType paymentType,
    Double totalAmount,
    Integer totalDays,
    ReservationStatus reservationStatus,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
  public static ReservationResponse fromEntity(Reservation reservation) {
    return new ReservationResponse(
        reservation.getId(),
        reservation.getCustomer(),
        reservation.getGroup(),
        reservation.getAccessories()
            .stream()
            .map(AccessoryDto::fromEntity)
            .toList(),
        reservation.getPickupDateTime(),
        reservation.getReturnDateTime(),
        reservation.getPaymentType(),
        reservation.getTotalAmount(),
        reservation.getTotalDays(),
        reservation.getReservationStatus(),
        reservation.getCreatedAt(),
        reservation.getUpdatedAt()
    );
  }
}

