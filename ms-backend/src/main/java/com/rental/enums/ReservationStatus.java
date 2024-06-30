package com.rental.enums;

/**
 * The enum ReservationStatus.
 */
public enum ReservationStatus {

  /**
   * Pending reservationStatus.
   */
  PENDING("Pending"),

  /**
   * Confirmed reservationStatus.
   */
  CONFIRMED("Confirmed"),

  /**
   * Cancelled reservationStatus.
   */
  CANCELLED("Cancelled");

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
