package com.rental.repository;

import com.rental.entity.Customer;
import com.rental.entity.Payment;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository <Payment, UUID> {

}
