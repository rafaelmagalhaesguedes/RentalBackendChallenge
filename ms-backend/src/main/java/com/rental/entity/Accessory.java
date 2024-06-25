package com.rental.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name = "accessories")
public class Accessory {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotBlank(message = "Name cannot be blank")
  @Size(max = 255, message = "Name must be less than or equal to 255 characters")
  private String name;

  @NotNull(message = "Daily rate cannot be null")
  @Positive(message = "Daily rate must be greater than zero")
  private Double dailyRate;

  public Accessory() { }

  public Accessory(String id) {
    this.id = UUID.fromString(id);
  }

  public Accessory(String name, Double dailyRate) {
    this.name = name;
    this.dailyRate = dailyRate;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = UUID.fromString(String.valueOf(id));
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getDailyRate() {
    return dailyRate;
  }

  public void setDailyRate(Double dailyRate) {
    this.dailyRate = dailyRate;
  }
}
