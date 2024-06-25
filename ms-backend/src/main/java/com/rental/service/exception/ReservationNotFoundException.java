package com.rental.service.exception;

public class ReservationNotFoundException extends NotFoundException {
  public ReservationNotFoundException() {
    super("Reservation not found");
  }
}
