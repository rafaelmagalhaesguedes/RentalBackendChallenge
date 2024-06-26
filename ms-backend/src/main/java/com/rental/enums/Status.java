package com.rental.enums;

public enum Status {
  PENDING("Pending"),
  CONFIRMED("Confirmed"),
  CANCELLED("Cancelled");

  private final String name;

  Status(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
