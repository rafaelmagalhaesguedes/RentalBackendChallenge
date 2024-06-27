package com.rental.controller.dto.vehicle;

import com.rental.entity.Group;
import com.rental.entity.Vehicle;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * The type Group dto.
 */
public record VehicleDto(
    UUID id,
    String model,
    String licensePlate,
    String brand,
    String color,
    String yearOfManufacture
) {

  /**
   * From entity group dto.
   *
   * @param vehicle the group
   * @return the group dto
   */
  public static VehicleDto fromEntity(Vehicle vehicle) {
    return new VehicleDto(
        vehicle.getId(),
        vehicle.getModel(),
        vehicle.getLicensePlate(),
        vehicle.getBrand(),
        vehicle.getColor(),
        vehicle.getYearOfManufacture()
    );
  }
}
