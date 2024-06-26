package com.rental.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Date;
import java.util.UUID;

@Entity
public class Vehicle {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String model;

  private String licensePlate;

  private String brand;

  private String color;

  private Date yearOfManufacture;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "group_id", nullable = false)
  private Group group;

  public Vehicle() { }

  public Vehicle(UUID id, String model, String licensePlate, String brand, String color,
      Date yearOfManufacture, Group group) {
    this.id = id;
    this.model = model;
    this.licensePlate = licensePlate;
    this.brand = brand;
    this.color = color;
    this.yearOfManufacture = yearOfManufacture;
    this.group = group;
  }

  public Vehicle(String model, String licensePlate, String brand, String color,
      Date yearOfManufacture, Group group) {
    this.model = model;
    this.licensePlate = licensePlate;
    this.brand = brand;
    this.color = color;
    this.yearOfManufacture = yearOfManufacture;
    this.group = group;
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

  public Date getYearOfManufacture() {
    return yearOfManufacture;
  }

  public void setYearOfManufacture(Date yearOfManufacture) {
    this.yearOfManufacture = yearOfManufacture;
  }

  public Group getGroup() {
    return group;
  }

  public void setGroup(Group group) {
    this.group = group;
  }
}
