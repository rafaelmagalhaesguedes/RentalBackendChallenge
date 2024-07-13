package com.rental.controller.dto.group;

import com.rental.entity.Group;
import com.rental.entity.Vehicle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.List;

/**
 * The type Group creation dto.
 */
public record GroupCreationDto(
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 255, message = "Name must be less than or equal to 255 characters")
    String name,

    @NotBlank(message = "Vehicle cannot be blank")
    @Size(max = 255, message = "Vehicle must be less than or equal to 255 characters")
    String vehicles,

    @Positive(message = "Daily rate is a positive number")
    Double dailyRate,

    String imageURL
) {

  /**
   * To entity group.
   *
   * @return the group
   */
  public Group toEntity() {
    return new Group(name, vehicles, dailyRate, imageURL);
  }
}
