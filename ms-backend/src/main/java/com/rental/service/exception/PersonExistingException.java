package com.rental.service.exception;

public class PersonExistingException extends NotFoundException {
  public PersonExistingException() {
    super("Person all ready exists.");
  }
}
