package com.rental.controller.dto.payment;

import com.rental.controller.dto.reservation.ReservationDto;
import com.rental.entity.Payment;
import com.rental.enums.PaymentStatus;
import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentResponseDto(
    UUID id,
    ReservationDto reservation,
    Double amount,
    PaymentStatus paymentStatus,
    LocalDateTime paymentDate
) {
  public static PaymentResponseDto fromEntity(Payment payment) {
    return new PaymentResponseDto(
        payment.getId(),
        ReservationDto.fromEntity(payment.getReservation()),
        payment.getAmount(),
        payment.getStatus(),
        payment.getPaymentDate()
    );
  }
}
