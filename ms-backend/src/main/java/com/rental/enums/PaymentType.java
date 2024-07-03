package com.rental.enums;

/**
 * The enum Payment method.
 */
public enum PaymentType {

  /**
   * The Pay at counter.
   */
  OFFLINE_PAYMENT("Offline payment"),

  /**
   * The Online payment.
   */
  ONLINE_PAYMENT("Online payment");

  private final String name;

  PaymentType(String name) {
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
