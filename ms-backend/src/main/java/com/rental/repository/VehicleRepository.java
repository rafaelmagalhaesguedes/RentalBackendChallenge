package com.rental.repository;

import com.rental.entity.Vehicle;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Vehicle repository.
 */
public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {

}
