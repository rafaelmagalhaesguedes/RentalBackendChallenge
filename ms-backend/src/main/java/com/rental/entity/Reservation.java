package com.rental.entity;

import com.rental.enums.PaymentType;
import com.rental.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_reservations")
public class Reservation {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "group_id", nullable = false)
  private Group group;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id", nullable = false)
  private Customer customer;

  @ManyToMany
  @JoinTable(name = "reservation_accessories",
             joinColumns = @JoinColumn(name = "reservation_id"),
             inverseJoinColumns = @JoinColumn(name = "accessory_id"))
  private List<Accessory> accessories = new ArrayList<>();

  @Column(name = "pickup_date_time")
  private LocalDateTime pickupDateTime;

  @Column(name = "return_date_time")
  private LocalDateTime returnDateTime;

  @Column(name = "payment_type")
  private PaymentType paymentType;

  @Column(name = "total_amount")
  private Double totalAmount;

  @Column(name = "total_days")
  private Integer totalDays;

  @Enumerated(EnumType.STRING)
  @Column(name = "reservation_status")
  private ReservationStatus reservationStatus;

  @Column(name = "crated_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
}