package com.rental.service.exception;

public class CustomerNotFoundException extends NotFoundException {
  public CustomerNotFoundException() {
    super("Customer not found");
  }
}
