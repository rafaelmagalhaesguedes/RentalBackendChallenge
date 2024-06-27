package com.rental.enums;

/**
 * The enum Status.
 */
public enum Status {

  /**
   * Pending status.
   */
  PENDING("Pending"),

  /**
   * Confirmed status.
   */
  CONFIRMED("Confirmed"),

  /**
   * Cancelled status.
   */
  CANCELLED("Cancelled");

  private final String name;

  Status(String name) {
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
