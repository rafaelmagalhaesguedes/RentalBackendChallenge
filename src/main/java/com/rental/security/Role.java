package com.rental.security;

/**
 * Enum representing a Role.
 */
public enum Role {
  ADMIN("ADMIN"),
  MANAGER("MANAGER"),
  USER("USER");

  private final String name;

  Role(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}