package com.rental.controller.dto.group;

import com.rental.entity.Group;
import java.util.UUID;

public record GroupDto(
    UUID id,
    String name,
    String vehicles,
    Double dailyRate
) {
  public static GroupDto fromEntity(Group group) {
    return new GroupDto(
        group.getId(),
        group.getName(),
        group.getVehicles(),
        group.getDailyRate()
    );
  }
}
