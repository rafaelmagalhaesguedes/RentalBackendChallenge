package com.rental.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import com.rental.controller.dto.reservation.ReservationCreationDto;
import com.rental.controller.dto.reservation.ReservationDto;
import com.rental.entity.Accessory;
import com.rental.entity.Group;
import com.rental.entity.Person;
import com.rental.entity.Reservation;
import com.rental.enums.ReservationStatus;
import com.rental.producer.ReservationProducer;
import com.rental.repository.AccessoryRepository;
import com.rental.repository.GroupRepository;
import com.rental.repository.PersonRepository;
import com.rental.repository.ReservationRepository;
import com.rental.service.PaymentService;
import com.rental.service.ReservationService;
import com.rental.service.exception.GroupNotFoundException;
import com.rental.service.exception.PersonNotFoundException;
import com.stripe.exception.StripeException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class ReservationServiceTest {

  @Autowired
  private ReservationService reservationService;

  @MockBean
  private ReservationRepository reservationRepository;

  @MockBean
  private PersonRepository personRepository;

  @MockBean
  private GroupRepository groupRepository;

  @MockBean
  private AccessoryRepository accessoryRepository;

  @MockBean
  private PaymentService paymentService;

  @MockBean
  private ReservationProducer reservationProducer;

  @Test
  public void testCreateReservation() throws PersonNotFoundException, GroupNotFoundException, StripeException {
    // Arrange
    UUID personId = UUID.randomUUID();
    UUID groupId = UUID.randomUUID();
    List<UUID> accessoryIds = Collections.singletonList(UUID.randomUUID());
    LocalDateTime pickupDateTime = LocalDateTime.now();
    LocalDateTime returnDateTime = pickupDateTime.plusHours(3);
    String paymentMethod = "Counter";

    ReservationCreationDto reservationDto = new ReservationCreationDto(
        personId, groupId, accessoryIds, pickupDateTime, returnDateTime, paymentMethod
    );

    Person mockPerson = new Person();
    Mockito.when(personRepository.findById(personId)).thenReturn(java.util.Optional.of(mockPerson));

    Group mockGroup = new Group();
    Mockito.when(groupRepository.findById(groupId)).thenReturn(java.util.Optional.of(mockGroup));

    Accessory mockAccessory = new Accessory();
    Mockito.when(accessoryRepository.findAllById(accessoryIds)).thenReturn(Collections.singletonList(mockAccessory));

    Reservation mockReservation = new Reservation();
    Mockito.when(reservationRepository.save(any(Reservation.class))).thenReturn(mockReservation);

    // Act
    ReservationDto createdReservation = reservationService.createReservation(
        reservationDto.personId(), reservationDto.groupId(), reservationDto.accessoryIds(),
        reservationDto.pickupDateTime(), reservationDto.returnDateTime(),
        reservationDto.paymentType()
    );

    // Assert
    assertEquals(ReservationStatus.PENDING, createdReservation.reservationStatus());
  }

  @Test
  public void testCreateReservationPersonNotFoundException() throws GroupNotFoundException, StripeException {
    // Arrange
    UUID personId = UUID.randomUUID();
    UUID groupId = UUID.randomUUID();
    List<UUID> accessoryIds = Arrays.asList(UUID.randomUUID(), UUID.randomUUID());
    LocalDateTime pickupDateTime = LocalDateTime.now();
    LocalDateTime returnDateTime = pickupDateTime.plusHours(3);
    String paymentMethod = "Counter";

    Mockito.when(personRepository.findById(personId)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(PersonNotFoundException.class, () -> reservationService.createReservation(personId, groupId, accessoryIds, pickupDateTime, returnDateTime, paymentMethod));
  }

  @Test
  public void testCreateReservationGroupNotFoundException() throws PersonNotFoundException, StripeException {
    // Arrange
    UUID personId = UUID.randomUUID();
    UUID groupId = UUID.randomUUID();
    List<UUID> accessoryIds = Arrays.asList(UUID.randomUUID(), UUID.randomUUID());
    LocalDateTime pickupDateTime = LocalDateTime.now();
    LocalDateTime returnDateTime = pickupDateTime.plusHours(3);
    String paymentType = "Counter";

    Person person = new Person();
    person.setId(personId);

    Mockito.when(personRepository.findById(personId)).thenReturn(Optional.of(person));
    Mockito.when(groupRepository.findById(groupId)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(GroupNotFoundException.class, () -> reservationService.createReservation(personId, groupId, accessoryIds, pickupDateTime, returnDateTime, paymentType));
  }
}

