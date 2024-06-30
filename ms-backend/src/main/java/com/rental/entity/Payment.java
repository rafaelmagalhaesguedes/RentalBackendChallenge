package com.rental.entity;

import com.rental.enums.ReservationStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The type Payment.
 */
@Entity
@Table(name = "payments")
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne
  private Reservation reservation;
  private Double amount;
  private ReservationStatus reservationStatus; // "Pending", "Completed", "Failed"
  private LocalDateTime paymentDate;
  private String paymentMethod; // "Online", "Counter"

  /**
   * Instantiates a new Payment.
   */
  public Payment() { }

  /**
   * Instantiates a new Payment.
   *
   * @param id            the id
   * @param reservation   the reservation
   * @param amount        the amount
   * @param reservationStatus        the reservationStatus
   * @param paymentDate   the payment date
   * @param paymentMethod the payment method
   */
  public Payment(UUID id, Reservation reservation, Double amount, ReservationStatus reservationStatus,
      LocalDateTime paymentDate, String paymentMethod) {
    this.id = id;
    this.reservation = reservation;
    this.amount = amount;
    this.reservationStatus = reservationStatus;
    this.paymentDate = paymentDate;
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
   * Gets reservation.
   *
   * @return the reservation
   */
  public Reservation getReservation() {
    return reservation;
  }

  /**
   * Sets reservation.
   *
   * @param reservation the reservation
   */
  public void setReservation(Reservation reservation) {
    this.reservation = reservation;
  }

  /**
   * Gets amount.
   *
   * @return the amount
   */
  public Double getAmount() {
    return amount;
  }

  /**
   * Sets amount.
   *
   * @param amount the amount
   */
  public void setAmount(Double amount) {
    this.amount = amount;
  }

  /**
   * Gets reservationStatus.
   *
   * @return the reservationStatus
   */
  public ReservationStatus getStatus() {
    return reservationStatus;
  }

  /**
   * Sets reservationStatus.
   *
   * @param reservationStatus the reservationStatus
   */
  public void setStatus(ReservationStatus reservationStatus) {
    this.reservationStatus = reservationStatus;
  }

  /**
   * Gets payment date.
   *
   * @return the payment date
   */
  public LocalDateTime getPaymentDate() {
    return paymentDate;
  }

  /**
   * Sets payment date.
   *
   * @param paymentDate the payment date
   */
  public void setPaymentDate(LocalDateTime paymentDate) {
    this.paymentDate = paymentDate;
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
