package com.rental.controller.dto.reservation;

import com.rental.controller.dto.accessory.AccessoryDto;
import com.rental.controller.dto.person.PersonDto;
import com.rental.entity.Reservation;
import com.rental.enums.ReservationStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import com.rental.entity.Group;

/**
 * The type Reservation dto.
 */
public record ReservationDto(
    UUID id,
    PersonDto person,
    Group group,
    List<AccessoryDto> accessories,
    LocalDateTime pickupDateTime,
    LocalDateTime returnDateTime,
    Double totalAmount,
    Integer totalDays,
    ReservationStatus reservationStatus,
    String paymentType,
    LocalDateTime createdDate,
    String paymentUrl
) {

  /**
   * From entity reservation dto.
   *
   * @param reservation the reservation
   * @param paymentUrl  the payment url
   * @return the reservation dto
   */
  public static ReservationDto fromEntity(Reservation reservation, String paymentUrl) {
    return new ReservationDto(
        reservation.getId(),
        PersonDto.fromEntity(reservation.getPerson()),
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
        reservation.getPaymentType(),
        reservation.getCreatedDate(),
        paymentUrl
    );
  }
}

