package com.rental.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.List;

public class Reservation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Customer customer;

  @ManyToOne
  private VehicleGroup vehicleGroup;

  @ManyToMany
  private List<Accessory> accessories;

  private LocalDateTime pickupDateTime;
  private LocalDateTime returnDateTime;
  private double totalAmount;
  private String status; // "Pending", "Confirmed", "Cancelled"
  private String paymentMethod; // "Pay at Counter", "Online Payment"

  public Reservation(Customer customer, VehicleGroup vehicleGroup, List<Accessory> accessories,
      LocalDateTime pickupDateTime, LocalDateTime returnDateTime, double totalAmount, String status,
      String paymentMethod) {
    this.customer = customer;
    this.vehicleGroup = vehicleGroup;
    this.accessories = accessories;
    this.pickupDateTime = pickupDateTime;
    this.returnDateTime = returnDateTime;
    this.totalAmount = totalAmount;
    this.status = status;
    this.paymentMethod = paymentMethod;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public VehicleGroup getVehicleGroup() {
    return vehicleGroup;
  }

  public void setVehicleGroup(VehicleGroup vehicleGroup) {
    this.vehicleGroup = vehicleGroup;
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

  public double getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(double totalAmount) {
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
