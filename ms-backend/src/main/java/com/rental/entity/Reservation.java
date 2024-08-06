package com.rental.entity;

import com.rental.enums.PaymentType;
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

  private Integer totalDays;

  @Enumerated(EnumType.STRING)
  private PaymentType paymentType;

  @Enumerated(EnumType.STRING)
  private ReservationStatus reservationStatus;

  private LocalDateTime createdDate;

  public Reservation() { }

  public Reservation(UUID id, Person person, Group group, List<Accessory> accessories,
      LocalDateTime pickupDateTime, LocalDateTime returnDateTime, Double totalAmount, Integer totalDays,
      ReservationStatus reservationStatus, PaymentType paymentType) {
    this.id = id;
    this.person = person;
    this.group = group;
    this.accessories = accessories;
    this.pickupDateTime = pickupDateTime;
    this.returnDateTime = returnDateTime;
    this.totalAmount = totalAmount;
    this.totalDays = totalDays;
    this.reservationStatus = reservationStatus;
    this.paymentType = paymentType;
  }

  public Reservation(UUID id, Person person, Group group, List<Accessory> accessories,
                     LocalDateTime pickupDateTime, LocalDateTime returnDateTime, Double totalAmount, Integer totalDays) {
    this.id = id;
    this.person = person;
    this.group = group;
    this.accessories = accessories;
    this.pickupDateTime = pickupDateTime;
    this.returnDateTime = returnDateTime;
    this.totalAmount = totalAmount;
    this.totalDays = totalDays;
  }

  public Reservation(Person person, Group group, List<Accessory> accessories, LocalDateTime localDateTime, LocalDateTime pickupDateTime, double totalAmount, int totalDays, ReservationStatus reservationStatus,
      String s, LocalDateTime now) {
  }

  public Reservation(Person mockPerson, Group mockGroup, LocalDateTime now, LocalDateTime localDateTime, PaymentType paymentType) {
  }

  public Reservation(Person mockPerson, Group mockGroup, LocalDateTime now, LocalDateTime localDateTime, double totalAmount, int totalDays) {
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
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

  public PaymentType getPaymentType() {
    return paymentType;
  }

  public void setPaymentType(PaymentType paymentType) {
    this.paymentType = paymentType;
  }
}
