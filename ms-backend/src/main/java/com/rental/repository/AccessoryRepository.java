package com.rental.repository;

import com.rental.entity.Accessory;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessoryRepository extends JpaRepository <Accessory, UUID> {

}
