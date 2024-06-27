package com.rental.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.UUID;

/**
 * The type Group.
 */
@Entity
@Table(name = "vehicle_groups")
public class Group {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String name;

  @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Vehicle> vehicles;

  private Double dailyRate;

  /**
   * Instantiates a new Group.
   */
  public Group() { }

  /**
   * Instantiates a new Group.
   *
   * @param name      the name
   * @param vehicles  the vehicles
   * @param dailyRate the daily rate
   */
  public Group(String name, List<Vehicle> vehicles, Double dailyRate) {
    this.name = name;
    this.vehicles = vehicles;
    this.dailyRate = dailyRate;
  }

  /**
   * Gets id.
   *
   * @return the id
   */
  public UUID getId() {
    return id;
  }

  /**
   * Sets id.
   *
   * @param id the id
   */
  public void setId(UUID id) {
    this.id = id;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets name.
   *
   * @param name the name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets daily rate.
   *
   * @return the daily rate
   */
  public Double getDailyRate() {
    return dailyRate;
  }

  /**
   * Sets daily rate.
   *
   * @param dailyRate the daily rate
   */
  public void setDailyRate(Double dailyRate) {
    this.dailyRate = dailyRate;
  }

  /**
   * Gets vehicles.
   *
   * @return the vehicles
   */
  public List<Vehicle> getVehicles() {
    return vehicles;
  }

  /**
   * Sets vehicles.
   *
   * @param vehicles the vehicles
   */
  public void setVehicles(List<Vehicle> vehicles) {
    this.vehicles = vehicles;
  }
}
