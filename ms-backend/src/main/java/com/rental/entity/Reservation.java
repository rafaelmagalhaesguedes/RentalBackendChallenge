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

  private Integer totalDays;

  private String paymentType; // "Online", "Counter"

  @Enumerated(EnumType.STRING)
  private ReservationStatus reservationStatus; // "Pending", "Confirmed", "Cancelled"

  private LocalDateTime createdDate;

  /**
   * Instantiates a new Reservation.
   */
  public Reservation() { }

  /**
   * Instantiates a new Reservation.
   *
   * @param id                the id
   * @param person            the person
   * @param group             the group
   * @param accessories       the accessories
   * @param pickupDateTime    the pickup date time
   * @param returnDateTime    the return date time
   * @param totalAmount       the total amount
   * @param totalDays         the total days
   * @param reservationStatus the reservationStatus
   * @param paymentType       the payment method
   */
  public Reservation(UUID id, Person person, Group group, List<Accessory> accessories,
      LocalDateTime pickupDateTime, LocalDateTime returnDateTime, Double totalAmount, Integer totalDays,
      ReservationStatus reservationStatus, String paymentType) {
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

  /**
   * Instantiates a new Reservation.
   *
   * @param person            the person
   * @param group             the group
   * @param accessories       the accessories
   * @param localDateTime     the local date time
   * @param pickupDateTime    the pickup date time
   * @param totalAmount       the total amount
   * @param totalDays         the total days
   * @param reservationStatus the reservation status
   * @param s                 the s
   * @param now               the now
   */
  public Reservation(Person person, Group group, List<Accessory> accessories, LocalDateTime localDateTime, LocalDateTime pickupDateTime, double totalAmount, int totalDays, ReservationStatus reservationStatus,
      String s, LocalDateTime now) {
  }

  /**
   * Instantiates a new Reservation.
   *
   * @param mockPerson    the mock person
   * @param mockGroup     the mock group
   * @param now           the now
   * @param localDateTime the local date time
   * @param paymentType   the payment type
   */
  public Reservation(Person mockPerson, Group mockGroup, LocalDateTime now, LocalDateTime localDateTime, PaymentType paymentType) {
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
   * Gets total days.
   *
   * @return the total days
   */
  public Integer getTotalDays() {
    return totalDays;
  }

  /**
   * Sets total days.
   *
   * @param totalDays the total days
   */
  public void setTotalDays(Integer totalDays) {
    this.totalDays = totalDays;
  }

  /**
   * Gets created date.
   *
   * @return the created date
   */
  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  /**
   * Sets created date.
   *
   * @param createdDate the created date
   */
  public void setCreatedDate(LocalDateTime createdDate) {
    this.createdDate = createdDate;
  }

  /**
   * Gets reservationStatus.
   *
   * @return the reservationStatus
   */
  public ReservationStatus getReservationStatus() {
    return reservationStatus;
  }

  /**
   * Sets reservationStatus.
   *
   * @param reservationStatus the reservationStatus
   */
  public void setReservationStatus(ReservationStatus reservationStatus) {
    this.reservationStatus = reservationStatus;
  }

  /**
   * Gets payment method.
   *
   * @return the payment method
   */
  public String getPaymentType() {
    return paymentType;
  }

  /**
   * Sets payment method.
   *
   * @param paymentType the payment method
   */
  public void setPaymentType(String paymentType) {
    this.paymentType = paymentType;
  }
}
