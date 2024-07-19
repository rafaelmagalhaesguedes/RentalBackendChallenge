package com.rental.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.apache.commons.lang3.builder.EqualsBuilder;

import java.util.UUID;

/**
 * The type Vehicle.
 */
@Entity
public class Vehicle {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String model;

  private String licensePlate;

  private String brand;

  private String color;

  private String yearOfManufacture;

  public Vehicle() { }

  /**
   * Instantiates a new Vehicle.
   *
   * @param id                the id
   * @param model             the model
   * @param licensePlate      the license plate
   * @param brand             the brand
   * @param color             the color
   * @param yearOfManufacture the year of manufacture
   */
  public Vehicle(UUID id, String model, String licensePlate, String brand, String color,
      String yearOfManufacture) {
    this.id = id;
    this.model = model;
    this.licensePlate = licensePlate;
    this.brand = brand;
    this.color = color;
    this.yearOfManufacture = yearOfManufacture;
  }

  /**
   * Instantiates a new Vehicle.
   *
   * @param model             the model
   * @param licensePlate      the license plate
   * @param brand             the brand
   * @param color             the color
   * @param yearOfManufacture the year of manufacture
   */
  public Vehicle(String model, String licensePlate, String brand, String color,
      String yearOfManufacture) {
    this.model = model;
    this.licensePlate = licensePlate;
    this.brand = brand;
    this.color = color;
    this.yearOfManufacture = yearOfManufacture;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getLicensePlate() {
    return licensePlate;
  }

  public void setLicensePlate(String licensePlate) {
    this.licensePlate = licensePlate;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getYearOfManufacture() {
    return yearOfManufacture;
  }

  public void setYearOfManufacture(String yearOfManufacture) {
    this.yearOfManufacture = yearOfManufacture;
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(obj, this);
  }
}
