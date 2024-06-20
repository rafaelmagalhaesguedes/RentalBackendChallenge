package com.rental.repository;

import com.rental.entity.VehicleGroup;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleGroupRepository extends JpaRepository<VehicleGroup, UUID> {

}
