package com.rental.config.database;

import com.rental.entity.Accessory;
import com.rental.entity.Group;
import com.rental.entity.Person;
import com.rental.entity.Reservation;
import com.rental.enums.ReservationStatus;
import com.rental.repository.AccessoryRepository;
import com.rental.repository.GroupRepository;
import com.rental.repository.PersonRepository;
import com.rental.repository.ReservationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
class ReservationDataSeeder implements CommandLineRunner {

    private final AccessoryRepository accessoryRepository;
    private final GroupRepository groupRepository;
    private final PersonRepository personRepository;
    private final ReservationRepository reservationRepository;

    public ReservationDataSeeder(AccessoryRepository accessoryRepository, GroupRepository groupRepository,
                                 PersonRepository personRepository, ReservationRepository reservationRepository) {
        this.accessoryRepository = accessoryRepository;
        this.groupRepository = groupRepository;
        this.personRepository = personRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        seedReservations();
    }

    private void seedReservations() {
        List<Reservation> reservations = new ArrayList<>();

        Person person1 = personRepository.findByEmail("joao.silva@example.com").orElseThrow();
        Person person2 = personRepository.findByEmail("maria.oliveira@example.com").orElseThrow();
        Group groupA = groupRepository.findByName("Grupo A").orElseThrow();
        Group groupB = groupRepository.findByName("Grupo B").orElseThrow();

        List<Accessory> accessories = accessoryRepository.findAll();

        reservations.add(new Reservation(UUID.randomUUID(), person1, groupA, accessories, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(5), 200.00, 4, ReservationStatus.CONFIRMED, "Online"));
        reservations.add(new Reservation(UUID.randomUUID(), person2, groupB, accessories, LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(6), 220.00, 4, ReservationStatus.CONFIRMED, "Counter"));

        reservationRepository.saveAll(reservations);
    }
}
