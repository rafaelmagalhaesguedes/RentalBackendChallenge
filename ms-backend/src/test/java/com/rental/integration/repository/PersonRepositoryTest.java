package com.rental.integration.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.rental.entity.Person;
import com.rental.repository.PersonRepository;
import com.rental.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testCreatePerson() {
        // Arrange
        Person person = new Person("Rafael Guedes", "rafa", "rafa@email.com", "password", Role.MANAGER);

        personRepository.save(person);

        // Act
        Person sut = testEntityManager.find(Person.class, person.getId());

        // Assert
        assertThat(sut).isNotNull();
        assertThat(sut.getFullName()).isEqualTo(person.getFullName());
        assertThat(sut.getUsername()).isEqualTo(person.getUsername());
        assertThat(sut.getEmail()).isEqualTo(person.getEmail());
        assertThat(sut.getRole()).isEqualTo(person.getRole());
    }
}
