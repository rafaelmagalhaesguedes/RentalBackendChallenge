package com.rental.enums;

/**
 * The enum Payment method.
 */
public enum PaymentStatus {

  CONFIRMED("CONFIRMED"),

  CANCEL("CANCELLED"),

  PENDING("PENDING");

  private final String name;

  PaymentStatus(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }
}
