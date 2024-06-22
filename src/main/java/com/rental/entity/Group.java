package com.rental.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "vehicle_groups")
public class Group {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private String name;
  private String vehicles;
  private Double dailyRate;

  public Group() { }

  public Group(String name, String vehicles, Double dailyRate) {
    this.name = name;
    this.vehicles = vehicles;
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

  public Double getDailyRate() {
    return dailyRate;
  }

  public void setDailyRate(Double dailyRate) {
    this.dailyRate = dailyRate;
  }

  public String getVehicles() {
    return vehicles;
  }

  public void setVehicles(String vehicles) {
    this.vehicles = vehicles;
  }
}
