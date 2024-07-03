package com.rental.enums;

/**
 * The enum ReservationStatus.
 */
public enum ReservationStatus {

  /**
   * Pending reservationStatus.
   */
  PENDING("Reserva Pendente"),

  /**
   * Confirmed reservationStatus.
   */
  CONFIRMED("Reserva Confirmada"),

  /**
   * Cancelled reservationStatus.
   */
  CANCELLED("Reserva Cancelada");

  private final String name;

  ReservationStatus(String name) {
    this.name = name;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }
}
