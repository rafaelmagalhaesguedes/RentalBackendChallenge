package com.rental.unit;

import static com.rental.mock.ReservationMock.*;
import static com.rental.mock.PersonMock.*;
import static com.rental.mock.GroupMock.*;
import static com.rental.mock.AccessoryMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rental.controller.dto.reservation.ReservationCreationDto;
import com.rental.controller.dto.reservation.ReservationDto;
import com.rental.controller.dto.reservation.ReservationPaymentDto;
import com.rental.entity.Reservation;
import com.rental.mock.ReservationMock;
import com.rental.producer.ReservationProducer;
import com.rental.repository.ReservationRepository;
import com.rental.service.AccessoryService;
import com.rental.service.GroupService;
import com.rental.service.PaymentService;
import com.rental.service.PersonService;
import com.rental.service.ReservationService;
import com.rental.service.exception.PersonNotFoundException;
import com.rental.service.exception.GroupNotFoundException;
import com.rental.service.exception.ReservationNotFoundException;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * Unit Tests to Reservation Service Class
 * *
 * Created by Rafa Guedes
 * */
@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @InjectMocks
    ReservationService reservationService;

    @Mock
    ReservationRepository reservationRepository;

    @Mock
    PersonService personService;

    @Mock
    GroupService groupService;

    @Mock
    AccessoryService accessoryService;

    @Mock
    PaymentService paymentService;

    @Mock
    ReservationProducer reservationProducer;

    @Test
    public void testCreateReservationWithOnlinePayment() throws PersonNotFoundException, GroupNotFoundException, StripeException {
        // Arrange
        when(personService.getPersonById(PERSON_01.getId())).thenReturn(PERSON_01);
        when(groupService.getGroupById(GROUP_01.getId())).thenReturn(GROUP_01);
        when(accessoryService.getAccessoriesById(any())).thenReturn(Arrays.asList(ACCESSORY_01, ACCESSORY_02));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(RESERVATION_01);

        Session mockSession = ReservationMock.createMockSession();
        when(paymentService.createCheckoutSession(any(Double.class), any(String.class), any(String.class), any(Reservation.class)))
                .thenReturn(mockSession);

        ReservationCreationDto reservationDto = new ReservationCreationDto(
                PERSON_01.getId(), GROUP_01.getId(), Arrays.asList(ACCESSORY_01.getId(), ACCESSORY_02.getId()),
                RESERVATION_01.getPickupDateTime(), RESERVATION_01.getReturnDateTime(), RESERVATION_01.getTotalAmount(), RESERVATION_01.getTotalDays());

        // Act
        ReservationPaymentDto createdReservation = reservationService.createReservationWithOnlinePayment(reservationDto);

        // Assert
        assertThat(createdReservation).isNotNull();
        verify(personService).getPersonById(PERSON_01.getId());
        verify(groupService).getGroupById(GROUP_01.getId());
        verify(accessoryService).getAccessoriesById(any());
        verify(reservationRepository).save(any(Reservation.class));
        verify(paymentService).createCheckoutSession(any(Double.class), any(String.class), any(String.class), any(Reservation.class));
    }

    @Test
    public void testCreateReservationWithStorePayment() throws PersonNotFoundException, GroupNotFoundException {
        // Arrange
        when(personService.getPersonById(PERSON_01.getId())).thenReturn(PERSON_01);
        when(groupService.getGroupById(GROUP_01.getId())).thenReturn(GROUP_01);
        when(accessoryService.getAccessoriesById(any())).thenReturn(Arrays.asList(ACCESSORY_01, ACCESSORY_02));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(RESERVATION_01);

        ReservationCreationDto reservationDto = new ReservationCreationDto(
                PERSON_01.getId(), GROUP_01.getId(), Arrays.asList(ACCESSORY_01.getId(), ACCESSORY_02.getId()),
                RESERVATION_01.getPickupDateTime(), RESERVATION_01.getReturnDateTime(), RESERVATION_01.getTotalAmount(), RESERVATION_01.getTotalDays());

        // Act
        ReservationDto createdReservation = reservationService.createReservationWithStorePayment(reservationDto);

        // Assert
        assertThat(createdReservation).isNotNull();
        verify(personService).getPersonById(PERSON_01.getId());
        verify(groupService).getGroupById(GROUP_01.getId());
        verify(accessoryService).getAccessoriesById(any());
        verify(reservationRepository).save(any(Reservation.class));
        verify(reservationProducer).publishMessageEmail(any(), any());
    }

    @Test
    public void testCreateReservationPersonNotFound() throws PersonNotFoundException {
        // Arrange
        when(personService.getPersonById(any(UUID.class))).thenThrow(new PersonNotFoundException());

        ReservationCreationDto reservationDto = new ReservationCreationDto(
                UUID.randomUUID(), GROUP_01.getId(), Arrays.asList(ACCESSORY_01.getId(), ACCESSORY_02.getId()),
                RESERVATION_01.getPickupDateTime(), RESERVATION_01.getReturnDateTime(), RESERVATION_01.getTotalAmount(), RESERVATION_01.getTotalDays());

        // Act & Assert
        assertThrows(PersonNotFoundException.class, () -> reservationService.createReservationWithOnlinePayment(reservationDto));
    }

    @Test
    public void testCreateReservationGroupNotFound() throws PersonNotFoundException, GroupNotFoundException {
        // Arrange
        when(personService.getPersonById(PERSON_01.getId())).thenReturn(PERSON_01);
        when(groupService.getGroupById(any(UUID.class))).thenThrow(new GroupNotFoundException());

        ReservationCreationDto reservationDto = new ReservationCreationDto(
                PERSON_01.getId(), UUID.randomUUID(), Arrays.asList(ACCESSORY_01.getId(), ACCESSORY_02.getId()),
                RESERVATION_01.getPickupDateTime(), RESERVATION_01.getReturnDateTime(), RESERVATION_01.getTotalAmount(), RESERVATION_01.getTotalDays());

        // Act & Assert
        assertThrows(GroupNotFoundException.class, () -> reservationService.createReservationWithOnlinePayment(reservationDto));
    }

    @Test
    public void testGetReservationById() throws ReservationNotFoundException {
        // Arrange
        when(reservationRepository.findById(eq(RESERVATION_01.getId()))).thenReturn(Optional.of(RESERVATION_01));

        // Act
        Reservation reservationFromDb = reservationService.getReservationById(RESERVATION_01.getId());
        verify(reservationRepository).findById(RESERVATION_01.getId());

        // Assert
        assertThat(reservationFromDb).isEqualTo(RESERVATION_01);
    }

    @Test
    public void testGetReservationByIdNotFound() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(reservationRepository.findById(eq(id))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ReservationNotFoundException.class, () -> reservationService.getReservationById(id));
    }

    @Test
    public void testGetAllReservations() {
        // Arrange
        List<Reservation> reservations = Arrays.asList(RESERVATION_01, RESERVATION_02);
        Page<Reservation> page = new PageImpl<>(reservations);
        Pageable pageable = PageRequest.of(0, 2);

        // Act
        when(reservationRepository.findAll(pageable)).thenReturn(page);
        List<Reservation> getAllReservations = reservationService.getAllReservations(0, 2);

        // Assert
        assertEquals(2, getAllReservations.size());
        assertThat(getAllReservations).isEqualTo(reservations);
    }

    @Test
    public void testGetAllReservationsEmpty() {
        // Arrange
        Page<Reservation> page = new PageImpl<>(List.of());
        Pageable pageable = PageRequest.of(0, 2);

        // Act
        when(reservationRepository.findAll(pageable)).thenReturn(page);
        List<Reservation> getAllReservations = reservationService.getAllReservations(0, 2);

        // Assert
        assertThat(getAllReservations).isEmpty();
    }
}
