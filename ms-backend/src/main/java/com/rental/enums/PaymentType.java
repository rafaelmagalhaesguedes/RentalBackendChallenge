package com.rental.enums;

/**
 * The enum Payment method.
 */
public enum PaymentType {

  STORE_PAYMENT("STORE_PAYMENT"),

  ONLINE_PAYMENT("ONLINE_PAYMENT");

  private final String name;

  PaymentType(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }
}
