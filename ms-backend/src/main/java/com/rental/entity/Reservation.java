package com.rental.entity;

import com.rental.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * The type Reservation.
 */
@Entity
@Table(name = "reservations")
public class Reservation {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "person_id", nullable = false)
  private Person person;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "group_id", nullable = false)
  private Group group;

  @ManyToMany
  @JoinTable(
      name = "reservation_accessories",
      joinColumns = @JoinColumn(name = "reservation_id"),
      inverseJoinColumns = @JoinColumn(name = "accessory_id")
  )
  private List<Accessory> accessories;

  private LocalDateTime pickupDateTime;

  private LocalDateTime returnDateTime;

  private Double totalAmount;

  @Enumerated(EnumType.STRING)
  private Status status;                  // "Pending", "Confirmed", "Cancelled"
  private String paymentMethod;           // "Online", "Counter"

  /**
   * Instantiates a new Reservation.
   */
  public Reservation() { }

  /**
   * Instantiates a new Reservation.
   *
   * @param id             the id
   * @param person         the person
   * @param group          the group
   * @param accessories    the accessories
   * @param pickupDateTime the pickup date time
   * @param returnDateTime the return date time
   * @param totalAmount    the total amount
   * @param status         the status
   * @param paymentMethod  the payment method
   */
  public Reservation(UUID id, Person person, Group group, List<Accessory> accessories,
      LocalDateTime pickupDateTime, LocalDateTime returnDateTime, Double totalAmount, Status status,
      String paymentMethod) {
    this.id = id;
    this.person = person;
    this.group = group;
    this.accessories = accessories;
    this.pickupDateTime = pickupDateTime;
    this.returnDateTime = returnDateTime;
    this.totalAmount = totalAmount;
    this.status = status;
    this.paymentMethod = paymentMethod;
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
   * Gets person.
   *
   * @return the person
   */
  public Person getPerson() {
    return person;
  }

  /**
   * Sets person.
   *
   * @param person the person
   */
  public void setPerson(Person person) {
    this.person = person;
  }

  /**
   * Gets group.
   *
   * @return the group
   */
  public Group getGroup() {
    return group;
  }

  /**
   * Sets group.
   *
   * @param group the group
   */
  public void setGroup(Group group) {
    this.group = group;
  }

  /**
   * Gets accessories.
   *
   * @return the accessories
   */
  public List<Accessory> getAccessories() {
    return accessories;
  }

  /**
   * Sets accessories.
   *
   * @param accessories the accessories
   */
  public void setAccessories(List<Accessory> accessories) {
    this.accessories = accessories;
  }

  /**
   * Gets pickup date time.
   *
   * @return the pickup date time
   */
  public LocalDateTime getPickupDateTime() {
    return pickupDateTime;
  }

  /**
   * Sets pickup date time.
   *
   * @param pickupDateTime the pickup date time
   */
  public void setPickupDateTime(LocalDateTime pickupDateTime) {
    this.pickupDateTime = pickupDateTime;
  }

  /**
   * Gets return date time.
   *
   * @return the return date time
   */
  public LocalDateTime getReturnDateTime() {
    return returnDateTime;
  }

  /**
   * Sets return date time.
   *
   * @param returnDateTime the return date time
   */
  public void setReturnDateTime(LocalDateTime returnDateTime) {
    this.returnDateTime = returnDateTime;
  }

  /**
   * Gets total amount.
   *
   * @return the total amount
   */
  public Double getTotalAmount() {
    return totalAmount;
  }

  /**
   * Sets total amount.
   *
   * @param totalAmount the total amount
   */
  public void setTotalAmount(Double totalAmount) {
    this.totalAmount = totalAmount;
  }

  /**
   * Gets status.
   *
   * @return the status
   */
  public Status getStatus() {
    return status;
  }

  /**
   * Sets status.
   *
   * @param status the status
   */
  public void setStatus(Status status) {
    this.status = status;
  }

  /**
   * Gets payment method.
   *
   * @return the payment method
   */
  public String getPaymentMethod() {
    return paymentMethod;
  }

  /**
   * Sets payment method.
   *
   * @param paymentMethod the payment method
   */
  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }
}
