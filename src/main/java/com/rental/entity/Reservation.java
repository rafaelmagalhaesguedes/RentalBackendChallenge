package com.rental.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "reservations")
public class Reservation {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(columnDefinition = "BINARY(16)")
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id", nullable = false, columnDefinition = "BINARY(16)")
  private Customer customer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "group_id", nullable = false, columnDefinition = "BINARY(16)")
  private Group group;

  @ManyToMany
  @JoinTable(
      name = "reservation_accessories",
      joinColumns = @JoinColumn(name = "reservation_id", columnDefinition = "BINARY(16)"),
      inverseJoinColumns = @JoinColumn(name = "accessory_id", columnDefinition = "BINARY(16)")
  )
  private List<Accessory> accessories;

  private LocalDateTime pickupDateTime;
  private LocalDateTime returnDateTime;
  private Double totalAmount;
  private String status; // "Pending", "Confirmed", "Cancelled"
  private String paymentMethod; // "Pay at Counter", "Online Payment"

  public Reservation() { }

  public Reservation(UUID id, Customer customer, Group group, List<Accessory> accessories,
      LocalDateTime pickupDateTime, LocalDateTime returnDateTime, Double totalAmount, String status,
      String paymentMethod) {
    this.id = id;
    this.customer = customer;
    this.group = group;
    this.accessories = accessories;
    this.pickupDateTime = pickupDateTime;
    this.returnDateTime = returnDateTime;
    this.totalAmount = totalAmount;
    this.status = status;
    this.paymentMethod = paymentMethod;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public Group getGroup() {
    return group;
  }

  public void setGroup(Group group) {
    this.group = group;
  }

  public List<Accessory> getAccessories() {
    return accessories;
  }

  public void setAccessories(List<Accessory> accessories) {
    this.accessories = accessories;
  }

  public LocalDateTime getPickupDateTime() {
    return pickupDateTime;
  }

  public void setPickupDateTime(LocalDateTime pickupDateTime) {
    this.pickupDateTime = pickupDateTime;
  }

  public LocalDateTime getReturnDateTime() {
    return returnDateTime;
  }

  public void setReturnDateTime(LocalDateTime returnDateTime) {
    this.returnDateTime = returnDateTime;
  }

  public Double getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(Double totalAmount) {
    this.totalAmount = totalAmount;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getPaymentMethod() {
    return paymentMethod;
  }

  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }
}
