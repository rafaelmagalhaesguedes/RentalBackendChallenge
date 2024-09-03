package com.rental.entity;

import com.rental.enums.ReservationStatus;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "reservations")
public class Reservation {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String fullName;

  private String email;

  private String document;

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

  private Integer totalDays;

  @Enumerated(EnumType.STRING)
  private ReservationStatus reservationStatus;

  private LocalDateTime createdDate;

  public Reservation() { }

  public Reservation(UUID id, String fullName, String email, String document, Group group, List<Accessory> accessories, LocalDateTime pickupDateTime, LocalDateTime returnDateTime, Double totalAmount, Integer totalDays, ReservationStatus reservationStatus, LocalDateTime createdDate) {
    this.id = id;
    this.fullName = fullName;
    this.email = email;
    this.document = document;
    this.group = group;
    this.accessories = accessories;
    this.pickupDateTime = pickupDateTime;
    this.returnDateTime = returnDateTime;
    this.totalAmount = totalAmount;
    this.totalDays = totalDays;
    this.reservationStatus = reservationStatus;
    this.createdDate = createdDate;
  }

  public Reservation(String fullName, String email, String document, Group group, List<Accessory> accessories, LocalDateTime pickupDateTime, LocalDateTime returnDateTime, Double totalAmount, Integer totalDays, ReservationStatus reservationStatus, LocalDateTime createdDate) {
    this.fullName = fullName;
    this.email = email;
    this.document = document;
    this.group = group;
    this.accessories = accessories;
    this.pickupDateTime = pickupDateTime;
    this.returnDateTime = returnDateTime;
    this.totalAmount = totalAmount;
    this.totalDays = totalDays;
    this.reservationStatus = reservationStatus;
    this.createdDate = createdDate;
  }

  public Reservation(String fullName, String email, String document, Group group, List<Accessory> accessories, LocalDateTime pickupDateTime, LocalDateTime returnDateTime, Double totalAmount, Integer totalDays) {
    this.fullName = fullName;
    this.email = email;
    this.document = document;
    this.group = group;
    this.accessories = accessories;
    this.pickupDateTime = pickupDateTime;
    this.returnDateTime = returnDateTime;
    this.totalAmount = totalAmount;
    this.totalDays = totalDays;
  }

  public Reservation(String fullName, String email, String document, Group group, List<Accessory> accessories, LocalDateTime pickupDateTime, LocalDateTime returnDateTime) {
    this.fullName = fullName;
    this.email = email;
    this.document = document;
    this.group = group;
    this.accessories = accessories;
    this.pickupDateTime = pickupDateTime;
    this.returnDateTime = returnDateTime;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getDocument() {
    return document;
  }

  public void setDocument(String document) {
    this.document = document;
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

  public Integer getTotalDays() {
    return totalDays;
  }

  public void setTotalDays(Integer totalDays) {
    this.totalDays = totalDays;
  }

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(LocalDateTime createdDate) {
    this.createdDate = createdDate;
  }

  public ReservationStatus getReservationStatus() {
    return reservationStatus;
  }

  public void setReservationStatus(ReservationStatus reservationStatus) {
    this.reservationStatus = reservationStatus;
  }

  @Override
  public String toString() {
    return "Reservation{" +
            "id=" + id +
            ", fullName='" + fullName + '\'' +
            ", email='" + email + '\'' +
            ", document='" + document + '\'' +
            ", group=" + group +
            ", accessories=" + accessories +
            ", pickupDateTime=" + pickupDateTime +
            ", returnDateTime=" + returnDateTime +
            ", totalAmount=" + totalAmount +
            ", totalDays=" + totalDays +
            ", reservationStatus=" + reservationStatus +
            ", createdDate=" + createdDate +
            '}';
  }
}
