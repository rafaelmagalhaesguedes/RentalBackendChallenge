package com.rental.repository;

import com.rental.entity.Reservation;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository <Reservation, UUID> {

}
