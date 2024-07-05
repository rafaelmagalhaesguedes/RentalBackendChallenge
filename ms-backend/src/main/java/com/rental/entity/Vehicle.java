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

  /**
   * Instantiates a new Vehicle.
   */
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
   * Gets model.
   *
   * @return the model
   */
  public String getModel() {
    return model;
  }

  /**
   * Sets model.
   *
   * @param model the model
   */
  public void setModel(String model) {
    this.model = model;
  }

  /**
   * Gets license plate.
   *
   * @return the license plate
   */
  public String getLicensePlate() {
    return licensePlate;
  }

  /**
   * Sets license plate.
   *
   * @param licensePlate the license plate
   */
  public void setLicensePlate(String licensePlate) {
    this.licensePlate = licensePlate;
  }

  /**
   * Gets brand.
   *
   * @return the brand
   */
  public String getBrand() {
    return brand;
  }

  /**
   * Sets brand.
   *
   * @param brand the brand
   */
  public void setBrand(String brand) {
    this.brand = brand;
  }

  /**
   * Gets color.
   *
   * @return the color
   */
  public String getColor() {
    return color;
  }

  /**
   * Sets color.
   *
   * @param color the color
   */
  public void setColor(String color) {
    this.color = color;
  }

  /**
   * Gets year of manufacture.
   *
   * @return the year of manufacture
   */
  public String getYearOfManufacture() {
    return yearOfManufacture;
  }

  /**
   * Sets year of manufacture.
   *
   * @param yearOfManufacture the year of manufacture
   */
  public void setYearOfManufacture(String yearOfManufacture) {
    this.yearOfManufacture = yearOfManufacture;
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(obj, this);
  }
}
