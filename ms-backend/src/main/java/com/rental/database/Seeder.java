package com.rental.database;

import com.rental.entity.Group;
import com.rental.entity.Accessory;
import com.rental.entity.Person;
import com.rental.repository.PersonRepository;
import com.rental.repository.GroupRepository;
import com.rental.repository.AccessoryRepository;
import com.rental.repository.ReservationRepository;
import com.rental.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class Seeder {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private AccessoryRepository accessoryRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @PostConstruct
    @Transactional
    public void seedDatabase() {
        if (groupRepository.count() == 0) {
            seedGroups();
        }
        if (accessoryRepository.count() == 0) {
            seedAccessories();
        }
        if (personRepository.count() == 0) {
            seedPerson();
        }
    }

    private void seedPerson() {
        List<Person> persons = Arrays.asList(
                new Person(UUID.randomUUID(), "Admin", "Admin", "admin@email.com", "secret_admin", Role.ADMIN),
                new Person(UUID.randomUUID(), "User", "User", "user@email.com", "secret_user", Role.USER)
            );
        personRepository.saveAll(persons);
    }

    private void seedGroups() {
        List<Group> groups = Arrays.asList(
                new Group(UUID.randomUUID(), "Grupo A", "Mobi, Uno, Kwid ou similares", 120.00, "https://raw.githubusercontent.com/rafaelmagalhaesguedes/CDN-GitHub/main/images/a.png"),
                new Group(UUID.randomUUID(), "Grupo B", "Argo, HB20, Onix ou similares", 160.00, "https://raw.githubusercontent.com/rafaelmagalhaesguedes/CDN-GitHub/main/images/b.png"),
                new Group(UUID.randomUUID(), "Grupo C", "Chronos, HB20S, Onix Plus ou similares", 195.00, "https://raw.githubusercontent.com/rafaelmagalhaesguedes/CDN-GitHub/main/images/c.png"),
                new Group(UUID.randomUUID(), "Grupo E", "Chevrolet Spin ou Fiat Doblò", 240.00, "https://raw.githubusercontent.com/rafaelmagalhaesguedes/CDN-GitHub/main/images/e.png"),
                new Group(UUID.randomUUID(), "Grupo G", "Renegade, Compass, Tracker ou similares", 280.00, "https://raw.githubusercontent.com/rafaelmagalhaesguedes/CDN-GitHub/main/images/g.png"),
                new Group(UUID.randomUUID(), "Grupo P", "Hilux, S10, Ranger ou similares", 300.00, "https://raw.githubusercontent.com/rafaelmagalhaesguedes/CDN-GitHub/main/images/p.png")
        );
        groupRepository.saveAll(groups);
    }

    private void seedAccessories() {
        List<Accessory> accessories = Arrays.asList(
                new Accessory(UUID.randomUUID(), "GPS", "SmartPhone com gps integrado.", 5, 90.00),
                new Accessory(UUID.randomUUID(), "Cadeira de Bebê", "Cadeira de bebê até 5 anos.", 5, 30.00),
                new Accessory(UUID.randomUUID(), "Bebê conforto", "Bebê conforto até 5 anos.", 5, 40.00),
                new Accessory(UUID.randomUUID(), "Assento de elevação", "Assento de elevação até 5 anos.", 5, 40.00),
                new Accessory(UUID.randomUUID(), "Multicondutores", "Para mais de um condutor.", 1, 15.00),
                new Accessory(UUID.randomUUID(), "Condutor Júnior", "Para condutores recém habilitados.", 1, 15.00)
        );
        accessoryRepository.saveAll(accessories);
    }
}
