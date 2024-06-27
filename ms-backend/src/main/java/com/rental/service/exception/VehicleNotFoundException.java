package com.rental.service.exception;

/**
 * The type Vehicle not found exception.
 */
public class VehicleNotFoundException extends NotFoundException {

  /**
   * Instantiates a new Vehicle not found exception.
   */
  public VehicleNotFoundException() {
    super("Vehicle not found.");
  }
}
