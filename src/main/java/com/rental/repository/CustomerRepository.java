package com.rental.repository;

import com.rental.entity.Accessory;
import com.rental.entity.Customer;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository <Customer, UUID> {

}
