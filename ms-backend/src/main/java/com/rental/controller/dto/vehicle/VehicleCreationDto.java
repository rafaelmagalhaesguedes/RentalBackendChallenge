package com.rental.controller.dto.vehicle;

import com.rental.entity.Vehicle;
import java.util.Date;
import java.util.UUID;

/**
 * The type Group dto.
 */
public record VehicleCreationDto(
    String model,
    String licensePlate,
    String brand,
    String color,
    String yearOfManufacture
) {

  /**
   * To entity group dto.
   *
   * @return the group dto
   */
  public Vehicle toEntity() {
    return new Vehicle(model, licensePlate, brand, color, yearOfManufacture);
  }
}
