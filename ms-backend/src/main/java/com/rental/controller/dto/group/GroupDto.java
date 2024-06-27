package com.rental.controller.dto.group;

import com.rental.entity.Group;
import com.rental.entity.Vehicle;
import java.util.List;
import java.util.UUID;

/**
 * The type Group dto.
 */
public record GroupDto(
    UUID id,
    String name,
    String vehicles,
    Double dailyRate
) {

  /**
   * From entity group dto.
   *
   * @param group the group
   * @return the group dto
   */
  public static GroupDto fromEntity(Group group) {
    return new GroupDto(
        group.getId(),
        group.getName(),
        group.getVehicles(),
        group.getDailyRate()
    );
  }
}
