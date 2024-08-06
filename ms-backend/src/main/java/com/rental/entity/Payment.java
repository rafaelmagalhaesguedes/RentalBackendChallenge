package com.rental.entity;

import com.rental.enums.PaymentStatus;
import com.rental.enums.ReservationStatus;
import jakarta.persistence.*;

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

  @Enumerated(EnumType.STRING)
  private PaymentStatus paymentStatus;

  private LocalDateTime paymentDate;

  private String paymentMethod;

  public Payment() { }

  public Payment(UUID id, Reservation reservation, Double amount, PaymentStatus paymentStatus,
      LocalDateTime paymentDate, String paymentMethod) {
    this.id = id;
    this.reservation = reservation;
    this.amount = amount;
    this.paymentStatus = paymentStatus;
    this.paymentDate = paymentDate;
    this.paymentMethod = paymentMethod;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Reservation getReservation() {
    return reservation;
  }

  public void setReservation(Reservation reservation) {
    this.reservation = reservation;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public PaymentStatus getStatus() {
    return paymentStatus;
  }

  public void setStatus(PaymentStatus paymentStatus) {
    this.paymentStatus = paymentStatus;
  }

  public LocalDateTime getPaymentDate() {
    return paymentDate;
  }

  public void setPaymentDate(LocalDateTime paymentDate) {
    this.paymentDate = paymentDate;
  }

  public String getPaymentMethod() {
    return paymentMethod;
  }

  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }
}
