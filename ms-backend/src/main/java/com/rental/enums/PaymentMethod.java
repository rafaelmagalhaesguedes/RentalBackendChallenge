package com.rental.enums;

/**
 * The enum Payment method.
 */
public enum PaymentMethod {

  /**
   * The Pay at counter.
   */
  PAY_AT_COUNTER("Pay at Counter"),

  /**
   * The Online payment.
   */
  ONLINE_PAYMENT("Online Payment");

  private final String name;

  PaymentMethod(String name) {
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
