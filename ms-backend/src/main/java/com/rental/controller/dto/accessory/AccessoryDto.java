package com.rental.controller.dto.accessory;

import com.rental.entity.Accessory;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import java.util.UUID;

public record AccessoryDto(
    UUID id,
    String name,
    String description,
    Integer quantity,
    Double dailyRate
) {
  public static AccessoryDto fromEntity(Accessory accessory) {
    return new AccessoryDto(
        accessory.getId(),
        accessory.getName(),
        accessory.getDescription(),
        accessory.getQuantity(),
        accessory.getDailyRate()
    );
  }
}
