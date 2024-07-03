package com.rental.enums;

/**
 * The enum Payment method.
 */
public enum PaymentStatus {

  /**
   * The Pay at counter.
   */
  CONFIRMED("Payment confirmed successfully!"),

  /**
   * The Online payment.
   */
  CANCEL("Payment canceled!");

  private final String name;

  PaymentStatus(String name) {
    this.name = name;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
    return this.name;
  }
}
