package com.rental;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;

import com.rental.entity.Person;
import com.rental.repository.PersonRepository;
import com.rental.security.Role;
import com.rental.service.PersonService;
import com.rental.service.exception.PersonExistingException;
import com.rental.service.exception.PersonNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
public class PersonServiceTest {

  @Autowired
  PersonService personService;

  @MockBean
  PersonRepository personRepository;

  @Test
  public void testPersonRetrievalById() throws PersonNotFoundException {
    UUID id = UUID.randomUUID();
    Person person = new Person();
    person.setId(id);
    person.setName("Rafa Guedes");
    person.setEmail("rafa@gmail.com");

    Mockito.when(personRepository.findById(eq(id)))
        .thenReturn(Optional.of(person));

    Person personFromDb = personService.getPersonById(id);

    Mockito.verify(personRepository).findById(id);

    assertEquals(personFromDb.getId(), person.getId());
    assertEquals(personFromDb.getName(), person.getName());
    assertEquals(personFromDb.getEmail(), person.getEmail());
  }

  @Test
  public void testGetAllPersons() {

    // Arrange
    UUID customer1Id = UUID.randomUUID();
    UUID customer2Id = UUID.randomUUID();

    Person person1 = new Person();
    person1.setId(customer1Id);
    person1.setName("Rafa Guedes");
    person1.setEmail("rafa@email.com");

    Person person2 = new Person();
    person2.setId(customer2Id);
    person2.setName("Jairo Rodrigues");
    person2.setName("jairo@email.com");

    List<Person> people = Arrays.asList(person1, person2);

    Page<Person> page = new PageImpl<>(people);
    Pageable pageable = PageRequest.of(0, 2);

    // Act
    Mockito.when(personRepository.findAll(pageable)).thenReturn(page);

    List<Person> getAllPerson = personService.getAllPersons(0, 2);

    Mockito.verify(personRepository).findAll(pageable);

    // Assert
    assertEquals(2, getAllPerson.size());
    assertEquals(person1.getId(), people.get(0).getId());
    assertEquals(person1.getName(), people.get(0).getName());
    assertEquals(person1.getEmail(), people.get(0).getEmail());

    assertEquals(person2.getId(), people.get(1).getId());
    assertEquals(person2.getName(), people.get(1).getName());
    assertEquals(person2.getEmail(), people.get(1).getEmail());
  }

  @Test
  public void testCreatePerson() throws PersonExistingException {

    // Arrange
    Person person = new Person();
    person.setId(UUID.randomUUID());
    person.setUsername("rafalguedes");
    person.setEmail("rafa@email.com");
    person.setPassword("password");
    person.setRole(Role.USER);

    // Act
    Mockito.when(personRepository.save(person)).thenReturn(person);

    Person newPerson = personService.createPerson(person);

    Mockito.verify(personRepository).save(person);

    // Assert
    assertEquals(newPerson.getId(), person.getId());
    assertEquals(newPerson.getName(), person.getName());
    assertEquals(newPerson.getEmail(), person.getEmail());
  }

  @Test
  public void testUpdatePerson() throws PersonNotFoundException {

    // Arrange
    UUID id = UUID.randomUUID();

    Person existingPerson = new Person();
    existingPerson.setId(id);
    existingPerson.setName("Rafa Guedes");
    existingPerson.setEmail("rafa@email.com");

    Person updatedPerson = new Person();
    updatedPerson.setName("Rafael Magalhães Guedes");
    updatedPerson.setEmail("rafaelmguedes@email.com");

    Mockito.when(personRepository.findById(id)).thenReturn(Optional.of(existingPerson));
    Mockito.when(personRepository.save(existingPerson)).thenReturn(existingPerson);

    // Act
    Person result = personService.updateCustomer(id, updatedPerson);

    // Assert
    assertEquals(updatedPerson.getName(), result.getName());
    assertEquals(updatedPerson.getEmail(), result.getEmail());
  }

  @Test
  public void testUpdatePersonNotFoundException() {

    // Arrange
    UUID id = UUID.randomUUID();

    Person updatedPerson = new Person();
    updatedPerson.setName("Rafael Guedes");
    updatedPerson.setEmail("rafa@email.com");

    Mockito.when(personRepository.findById(id))
        .thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(PersonNotFoundException.class,
        () -> personService.updateCustomer(id, updatedPerson));
  }

  @Test
  public void testDeletePerson() throws PersonNotFoundException {

    // Arrange
    UUID id = UUID.randomUUID();
    Person existingPerson = new Person();
    existingPerson.setId(id);
    existingPerson.setName("Rafa Guedes");
    existingPerson.setEmail("rafael@email.com");

    Mockito.when(personRepository.findById(id))
        .thenReturn(Optional.of(existingPerson));

    // Act
    Person result = personService.deleteCustomer(id);

    // Assert
    assertEquals(existingPerson, result);
    Mockito.verify(personRepository).delete(existingPerson);
  }

  @Test
  public void testDeletePersonNotFoundException() {

    // Arrange
    UUID id = UUID.randomUUID();

    Mockito.when(personRepository.findById(id))
        .thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(PersonNotFoundException.class,
        () -> personService.deleteCustomer(id));
  }
}
