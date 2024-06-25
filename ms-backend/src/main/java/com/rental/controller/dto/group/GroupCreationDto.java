package com.rental.controller.dto.group;

import com.rental.entity.Group;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record GroupCreationDto(
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 255, message = "Name must be less than or equal to 255 characters")
    String name,

    @NotBlank(message = "Vehicles cannot be blank")
    @Size(max = 255, message = "Vehicles must be less than or equal to 255 characters")
    String vehicles,

    @Positive(message = "Daily rate is a positive number")
    Double dailyRate
) {
  public Group toEntity() {
    return new Group(name, vehicles, dailyRate);
  }
}
