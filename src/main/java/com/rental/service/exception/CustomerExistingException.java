package com.rental.service.exception;

public class CustomerExistingException extends NotFoundException {
  public CustomerExistingException() {
    super("User all ready exists.");
  }
}
