package com.rental.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;

import java.util.ArrayList;
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

  private String vehicles;

  private Double dailyRate;

  private String imageURL;

  public Group() { }

  /**
   * Instantiates a new Group.
   *
   * @param id        the id
   * @param name      the name
   * @param vehicles  the vehicles
   * @param dailyRate the daily rate
   */
  public Group(UUID id, String name, String vehicles, Double dailyRate, String imageURL) {
    this.id = id;
    this.name = name;
    this.vehicles = vehicles;
    this.dailyRate = dailyRate;
    this.imageURL = imageURL;
  }

  /**
   * Instantiates a new Group.
   *
   * @param name      the name
   * @param vehicles  the vehicles
   * @param dailyRate the daily rate
   */
  public Group(String name, String vehicles, Double dailyRate, String imageURL) {
    this.name = name;
    this.vehicles = vehicles;
    this.dailyRate = dailyRate;
    this.imageURL = imageURL;
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

  public String getImageURL() {
    return imageURL;
  }

  public void setImageURL(String imageURL) {
    this.imageURL = imageURL;
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(obj, this);
  }
}
