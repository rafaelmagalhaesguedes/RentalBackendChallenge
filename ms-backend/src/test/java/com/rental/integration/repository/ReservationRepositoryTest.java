package com.rental.integration.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.rental.entity.Group;
import com.rental.entity.Person;
import com.rental.entity.Reservation;
import com.rental.enums.PaymentType;
import com.rental.repository.ReservationRepository;
import com.rental.security.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;

@DataJpaTest
public class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testCreateReservation() {
        // Arrange
        Person mockPerson = new Person("Test 01", "test", "test01@email.com", "password", Role.MANAGER);
        Group mockGroup = new Group("Group 01", "group1", 200.00, "url");
        Reservation reservation = new Reservation(mockPerson, mockGroup, LocalDateTime.now(), LocalDateTime.now().plusDays(1), PaymentType.ONLINE_PAYMENT);

        // Persist associated entities first to avoid detached entity issue
        testEntityManager.persistAndFlush(mockPerson);
        testEntityManager.persistAndFlush(mockGroup);

        reservationRepository.save(reservation);

        // Act
        Reservation sut = testEntityManager.find(Reservation.class, reservation.getId());

        // Assert
        assertThat(sut).isNotNull();
        assertThat(sut.getId()).isEqualTo(reservation.getId());
        assertThat(sut.getPerson()).isEqualTo(reservation.getPerson());
        assertThat(sut.getGroup()).isEqualTo(reservation.getGroup());
        assertThat(sut.getPickupDateTime()).isEqualTo(reservation.getPickupDateTime());
        assertThat(sut.getReturnDateTime()).isEqualTo(reservation.getReturnDateTime());
        assertThat(sut.getPaymentType()).isEqualTo(reservation.getPaymentType());
    }
}
