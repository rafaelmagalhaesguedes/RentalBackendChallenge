package com.rental.enums;

/**
 * The enum ReservationStatus.
 */
public enum ReservationStatus {

  /**
   * Pending reservationStatus.
   */
  PENDING("Pending Reservation"),

  /**
   * Confirmed reservationStatus.
   */
  CONFIRMED("Confirmed reservation"),

  /**
   * Cancelled reservationStatus.
   */
  CANCELLED("Reservation Canceled");

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
