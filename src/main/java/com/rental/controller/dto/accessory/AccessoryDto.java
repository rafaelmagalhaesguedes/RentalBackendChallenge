package com.rental.controller.dto.accessory;

import com.rental.entity.Accessory;
import java.util.UUID;

public record AccessoryDto(
    UUID id,
    String name,
    Double dailyRate
) {
  public static AccessoryDto fromEntity(Accessory accessory) {
    return new AccessoryDto(
        accessory.getId(),
        accessory.getName(),
        accessory.getDailyRate()
    );
  }
}
