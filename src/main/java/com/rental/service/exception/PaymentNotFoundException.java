package com.rental.service.exception;

public class PaymentNotFoundException extends NotFoundException {
  public PaymentNotFoundException() {
    super("Payment not found");
  }
}
