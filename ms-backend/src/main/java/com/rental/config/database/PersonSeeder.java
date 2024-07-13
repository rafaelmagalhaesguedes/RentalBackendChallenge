package com.rental.config.database;

import com.rental.entity.Person;
import com.rental.repository.PersonRepository;
import com.rental.security.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
class PersonSeeder implements CommandLineRunner {

    private final PersonRepository personRepository;

    public PersonSeeder(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        seedPersons();
    }

    private void seedPersons() {
        List<Person> persons = new ArrayList<>();

        persons.add(new Person(UUID.randomUUID(), "João da Silva", "joao.silva", "joao.silva@example.com", "password", Role.USER));
        persons.add(new Person(UUID.randomUUID(), "Maria Oliveira", "maria.oliveira", "maria.oliveira@example.com", "password", Role.USER));
        persons.add(new Person(UUID.randomUUID(), "Admin User", "admin.user", "admin.user@example.com", "password", Role.ADMIN));

        personRepository.saveAll(persons);
    }
}
