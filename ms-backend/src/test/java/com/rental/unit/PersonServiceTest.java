package com.rental.unit;

import static com.rental.mock.PersonMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rental.entity.Person;
import com.rental.producer.PersonProducer;
import com.rental.repository.PersonRepository;
import com.rental.service.PersonService;
import com.rental.service.exception.PersonExistingException;
import com.rental.service.exception.PersonNotFoundException;
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
 * Unit Tests to Person Service Class
 * *
 * Created by Rafa Guedes
 * */
@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

  @InjectMocks
  PersonService personService;

  @Mock
  PersonRepository personRepository;

  @Mock
  private PersonProducer personProducer;

  @Test
  public void testPersonRetrievalById() throws PersonNotFoundException {
    // Arrange
    when(personRepository.findById(eq(PERSON_01.getId()))).thenReturn(Optional.of(PERSON_01));

    // Act
    Person personFromDb = personService.getPersonById(PERSON_01.getId());
    verify(personRepository).findById(PERSON_01.getId());

    // Assert
    assertThat(personFromDb).isEqualTo(PERSON_01);
  }

  @Test
  public void testPersonRetrievalByEmail() throws PersonNotFoundException {
    // Arrange
    when(personRepository.findByEmail(eq(PERSON_01.getEmail()))).thenReturn(Optional.of(PERSON_01));

    // Act
    Person personFromDb = personService.getPersonByEmail(PERSON_01.getEmail());
    verify(personRepository).findByEmail(PERSON_01.getEmail());

    // Assert
    assertThat(personFromDb).isEqualTo(PERSON_01);
  }

  @Test
  public void testPersonRetrievalByEmailNotFound() {
    // Arrange
    var falseEmail = "failed@failed.com";
    when(personRepository.findByEmail(eq(falseEmail))).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(PersonNotFoundException.class, () -> personService.getPersonByEmail(falseEmail));
  }

  @Test
  public void testPersonRetrievalByIdNotFound() {
    // Arrange
    UUID id = UUID.randomUUID();
    when(personRepository.findById(eq(id))).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(PersonNotFoundException.class, () -> personService.getPersonById(id));
  }

  @Test
  public void testGetAllPersons() {
    // Arrange
    List<Person> people = Arrays.asList(PERSON_01, PERSON_02);
    Page<Person> page = new PageImpl<>(people);
    Pageable pageable = PageRequest.of(0, 2);

    // Act
    when(personRepository.findAll(pageable)).thenReturn(page);
    List<Person> getAllPerson = personService.getAllPersons(0, 2);

    // Assert
    assertEquals(2, getAllPerson.size());
    assertThat(getAllPerson).isNotEmpty();
    assertThat(getAllPerson).hasSize(2);
    assertThat(getAllPerson).isEqualTo(people);
  }

  @Test
  public void testGetAllPersonsEmpty() {
    // Arrange
    Page<Person> page = new PageImpl<>(Arrays.asList());
    Pageable pageable = PageRequest.of(0, 2);

    // Act
    when(personRepository.findAll(pageable)).thenReturn(page);
    List<Person> getAllPerson = personService.getAllPersons(0, 2);

    // Assert
    assertThat(getAllPerson).isEmpty();
  }

  @Test
  public void testCreatePerson() throws PersonExistingException {
    // Arrange
    when(personRepository.save(PERSON_CREATION)).thenReturn(PERSON_CREATION);

    // Act
    Person newPerson = personService.createPerson(PERSON_CREATION);

    // Assert
    assertThat(newPerson).isEqualTo(PERSON_CREATION);
  }

  @Test
  public void testCreatePersonFail() {
    // Arrange
    when(personRepository.save(PERSON_CREATION)).thenThrow(new RuntimeException("Unexpected error"));

    // Act & Assert
    RuntimeException exception = assertThrows(RuntimeException.class, () -> personService.createPerson(PERSON_CREATION));

    assertThat(exception.getMessage()).isEqualTo("Unexpected error");
  }

  @Test
  public void testUpdatePerson() throws PersonNotFoundException {
    // Arrange
    when(personRepository.findById(PERSON_01.getId())).thenReturn(Optional.of(PERSON_01));
    when(personRepository.save(PERSON_01)).thenReturn(PERSON_01);

    // Act
    Person result = personService.updatePerson(PERSON_01.getId(), PERSON_UPDATE);

    // Assert
    assertThat(result).isEqualTo(PERSON_01);
  }

  @Test
  public void testUpdatePersonNotFoundException() {
    // Arrange
    when(personRepository.findById(PERSON_UPDATE.getId())).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(PersonNotFoundException.class, () -> personService.updatePerson(PERSON_UPDATE.getId(), PERSON_UPDATE));
  }

  @Test
  public void testDeletePerson() throws PersonNotFoundException {
    // Arrange
    when(personRepository.findById(PERSON_01.getId())).thenReturn(Optional.of(PERSON_01));

    // Act
    Person result = personService.deletePerson(PERSON_01.getId());

    // Assert
    assertEquals(PERSON_01, result);
    verify(personRepository).delete(PERSON_01);
  }

  @Test
  public void testDeletePersonNotFoundException() {
    // Arrange
    UUID id = UUID.randomUUID();
    when(personRepository.findById(id)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(PersonNotFoundException.class, () -> personService.deletePerson(id));
  }
}
