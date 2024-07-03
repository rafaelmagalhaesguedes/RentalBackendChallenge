package com.rental.controller.dto.payment;

import com.rental.entity.Payment;
import com.rental.enums.PaymentStatus;
import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentResponseDto(
    UUID id,
    PaymentStatus paymentStatus,
    LocalDateTime paymentDate
) {
  public static PaymentResponseDto fromEntity(Payment payment) {
    return new PaymentResponseDto(
        payment.getId(),
        payment.getStatus(),
        payment.getPaymentDate()
    );
  }
}
