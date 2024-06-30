package com.rental.controller.dto.reservation;

import com.rental.controller.dto.accessory.AccessoryDto;
import com.rental.controller.dto.person.PersonDto;
import com.rental.controller.dto.group.GroupDto;
import com.rental.entity.Reservation;
import com.rental.enums.ReservationStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * The type Reservation read dto.
 */
public record ReservationReadDto(
    UUID id,
    PersonDto person,
    GroupDto group,
    List<AccessoryDto> accessories,
    LocalDateTime pickupDateTime,
    LocalDateTime returnDateTime,
    Double totalAmount,
    Integer totalDays,
    ReservationStatus reservationStatus,
    String paymentType,
    LocalDateTime createdDate
) {

  /**
   * From entity reservation read dto.
   *
   * @param reservation the reservation
   * @return the reservation read dto
   */
  public static ReservationReadDto fromEntity(Reservation reservation) {
    return new ReservationReadDto(
        reservation.getId(),
        PersonDto.fromEntity(reservation.getPerson()),
        GroupDto.fromEntity(reservation.getGroup()),
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
        reservation.getCreatedDate()
    );
  }
}

