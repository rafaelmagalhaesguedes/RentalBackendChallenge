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
import org.apache.commons.lang3.builder.EqualsBuilder;

import java.util.UUID;

/**
 * The type Accessory.
 */
@Entity
@Table(name = "accessories")
public class Accessory {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String name;

  private String description;

  private Integer quantity;

  private Double dailyRate;

  /**
   * Instantiates a new Accessory.
   */
  public Accessory() { }

  /**
   * Instantiates a new Accessory.
   *
   * @param id          the id
   * @param name        the name
   * @param description the description
   * @param quantity    the quantity
   * @param dailyRate   the daily rate
   */
  public Accessory(UUID id, String name, String description, Integer quantity, Double dailyRate) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.quantity = quantity;
    this.dailyRate = dailyRate;
  }

  /**
   * Instantiates a new Accessory.
   *
   * @param name        the name
   * @param description the description
   * @param quantity    the quantity
   * @param dailyRate   the daily rate
   */
  public Accessory(String name, String description, Integer quantity, Double dailyRate) {
    this.name = name;
    this.description = description;
    this.quantity = quantity;
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
    this.id = UUID.fromString(String.valueOf(id));
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
   * Gets description.
   *
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets description.
   *
   * @param description the description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Gets quantity.
   *
   * @return the quantity
   */
  public Integer getQuantity() {
    return quantity;
  }

  /**
   * Sets quantity.
   *
   * @param quantity the quantity
   */
  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
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

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(obj, this);
  }
}
