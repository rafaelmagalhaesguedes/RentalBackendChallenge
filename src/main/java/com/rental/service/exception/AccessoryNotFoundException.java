package com.rental.service.exception;

public class AccessoryNotFoundException extends NotFoundException {
  public AccessoryNotFoundException() {
    super("Accessory not found");
  }
}
