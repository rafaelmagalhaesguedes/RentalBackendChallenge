package com.rental.controller.dto.accessory;

import com.rental.entity.Accessory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record AccessoryUpdateDto(
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 255, message = "Name must be less than or equal to 255 characters")
    String name,

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 255, message = "Description must be less than or equal to 255 characters")
    String description,

    @NotNull(message = "Quantity cannot be null")
    @Positive(message = "Quantity must be greater than zero")
    Integer quantity,

    @NotNull(message = "Daily rate cannot be null")
    @Positive(message = "Daily rate must be greater than zero")
    Double dailyRate
) {
  public Accessory toEntity() {
    return new Accessory(name, description, quantity, dailyRate);
  }
}
