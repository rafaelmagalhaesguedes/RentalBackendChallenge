package com.rental.service.exception;

public class GroupNotFoundException extends NotFoundException {
  public GroupNotFoundException() {
    super("Group not found.");
  }
}
