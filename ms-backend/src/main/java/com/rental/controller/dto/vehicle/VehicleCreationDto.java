package com.rental.controller.dto.vehicle;

import com.rental.entity.Group;
import com.rental.entity.Vehicle;
import java.util.UUID;

/**
 * The type VehicleCreationDto.
 */
public record VehicleCreationDto(
    String model,
    String licensePlate,
    String brand,
    String color,
    String yearOfManufacture
) {

  public Vehicle toEntity() {
    return new Vehicle(model, licensePlate, brand, color, yearOfManufacture);
  }
}
