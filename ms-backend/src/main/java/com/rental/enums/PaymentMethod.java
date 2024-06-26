package com.rental.enums;

public enum PaymentMethod {
  PAY_AT_COUNTER("Pay at Counter"),
  ONLINE_PAYMENT("Online Payment");

  private final String name;

  PaymentMethod(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }
}
