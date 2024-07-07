package com.rental.integration.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.rental.entity.Person;
import com.rental.repository.PersonRepository;
import com.rental.security.Role;
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

    @Test
    public void testFindPersonById() {
        // Arrange
        Person person = new Person("Rafael Guedes", "rafa", "rafa@email.com", "password", Role.MANAGER);

        testEntityManager.persistAndFlush(person);

        // Act
        Person foundPerson = personRepository.findById(person.getId())
                .orElse(null);

        // Assert
        assertThat(foundPerson).isNotNull();
        assertThat(foundPerson.getId()).isEqualTo(person.getId());
        assertThat(foundPerson.getFullName()).isEqualTo(person.getFullName());
        assertThat(foundPerson.getUsername()).isEqualTo(person.getUsername());
        assertThat(foundPerson.getEmail()).isEqualTo(person.getEmail());
        assertThat(foundPerson.getRole()).isEqualTo(person.getRole());
    }

    @Test
    public void testUpdatePerson() {
        // Arrange
        Person person = new Person("Rafael Guedes", "rafa", "rafa@email.com", "password", Role.MANAGER);

        testEntityManager.persistAndFlush(person);

        person.setFullName("Novo Nome");
        person.setUsername("novousername");

        Person updatedPerson = personRepository.save(person);

        // Act
        Person foundPerson = testEntityManager.find(Person.class, updatedPerson.getId());

        // Assert
        assertThat(foundPerson).isNotNull();
        assertThat(foundPerson.getId()).isEqualTo(updatedPerson.getId());
        assertThat(foundPerson.getFullName()).isEqualTo(updatedPerson.getFullName());
        assertThat(foundPerson.getUsername()).isEqualTo(updatedPerson.getUsername());
        assertThat(foundPerson.getEmail()).isEqualTo(updatedPerson.getEmail());
        assertThat(foundPerson.getRole()).isEqualTo(updatedPerson.getRole());
    }

    @Test
    public void testDeletePerson() {
        // Arrange
        Person person = new Person("Rafael Guedes", "rafa", "rafa@email.com", "password", Role.MANAGER);

        testEntityManager.persistAndFlush(person);

        personRepository.delete(person);

        // Act
        Person deletedPerson = testEntityManager.find(Person.class, person.getId());

        // Assert
        assertThat(deletedPerson).isNull();
    }
}
