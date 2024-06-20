package com.rental.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "accessories")
public class Accessory {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String name;
  private Boolean dailyRate;

  public Accessory(String name, Boolean dailyRate) {
    this.name = name;
    this.dailyRate = dailyRate;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean getDailyRate() {
    return dailyRate;
  }

  public void setDailyRate(Boolean dailyRate) {
    this.dailyRate = dailyRate;
  }
}
