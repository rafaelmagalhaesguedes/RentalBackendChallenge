package com.rental.service.reservation;

import com.rental.controller.dto.reservation.ReservationRequest;
import com.rental.entity.Reservation;
import com.rental.service.exception.CustomerNotFoundException;
import com.rental.service.exception.GroupNotFoundException;
import com.rental.service.exception.ReservationNotFoundException;

import java.util.List;
import java.util.UUID;

public interface IReservationService {

    Reservation createReservation(ReservationRequest reservationDto) throws CustomerNotFoundException, GroupNotFoundException;

    Reservation getReservationById(UUID id) throws ReservationNotFoundException;

    List<Reservation> getAllReservations(int pageNumber, int pageSize);
}
