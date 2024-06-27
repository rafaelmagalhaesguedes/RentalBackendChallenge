package com.rental.controller.dto.accessory;

import com.rental.entity.Accessory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * The type Accessory creation dto.
 */
public record AccessoryCreationDto(
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 255, message = "Name must be less than or equal to 255 characters")
    String name,

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 255, message = "Description must be less than or equal to 255 characters")
    String description,

    @Positive(message = "Quantity is positive number")
    Integer quantity,

    @NotNull(message = "Daily rate cannot be null")
    @Positive(message = "Daily rate must be greater than zero")
    Double dailyRate
) {

  /**
   * To entity accessory.
   *
   * @return the accessory
   */
  public Accessory toEntity() {
    return new Accessory(name, description, quantity, dailyRate);
  }
}
