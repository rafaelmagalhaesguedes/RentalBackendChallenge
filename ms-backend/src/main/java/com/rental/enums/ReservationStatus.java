package com.rental.enums;

/**
 * The enum ReservationStatus.
 */
public enum ReservationStatus {

  PENDING("PENDING"),

  CONFIRMED("CONFIRMED"),

  CANCELLED("CANCELLED");

  private final String name;

  ReservationStatus(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
