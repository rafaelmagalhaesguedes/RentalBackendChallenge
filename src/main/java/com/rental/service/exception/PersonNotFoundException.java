package com.rental.service.exception;

public class PersonNotFoundException extends NotFoundException {
  public PersonNotFoundException() {
    super("Person not found");
  }
}
