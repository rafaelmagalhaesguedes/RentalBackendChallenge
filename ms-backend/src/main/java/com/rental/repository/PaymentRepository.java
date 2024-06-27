package com.rental.repository;

import com.rental.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * The interface Payment repository.
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> { }
