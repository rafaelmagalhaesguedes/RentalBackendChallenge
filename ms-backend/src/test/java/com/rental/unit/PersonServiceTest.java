package com.rental.unit;

import static com.rental.mock.PersonMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.assertj.core.api.Assertions.assertThat;
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
import org.mockito.Mockito;
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
    when(personRepository.findById(eq(PERSON_WITH_ID_1.getId()))).thenReturn(Optional.of(PERSON_WITH_ID_1));

    // Act
    Person personFromDb = personService.getPersonById(PERSON_WITH_ID_1.getId());
    Mockito.verify(personRepository).findById(PERSON_WITH_ID_1.getId());

    // Assert
    assertThat(personFromDb).isEqualTo(PERSON_WITH_ID_1);
  }

  @Test
  public void testGetAllPersons() {
    // Arrange
    List<Person> people = Arrays.asList(PERSON_WITH_ID_1, PERSON_WITH_ID_2);
    Page<Person> page = new PageImpl<>(people);
    Pageable pageable = PageRequest.of(0, 2);

    // Act
    when(personRepository.findAll(pageable)).thenReturn(page);
    List<Person> getAllPerson = personService.getAllPersons(0, 2);

    // Assert
    assertEquals(2, getAllPerson.size());
    assertThat(getAllPerson).isEqualTo(people);
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
  public void testUpdatePerson() throws PersonNotFoundException {
    // Arrange
    when(personRepository.findById(PERSON_WITH_ID_1.getId())).thenReturn(Optional.of(PERSON_WITH_ID_1));
    when(personRepository.save(PERSON_WITH_ID_1)).thenReturn(PERSON_WITH_ID_1);

    // Act
    Person result = personService.updatePerson(PERSON_WITH_ID_1.getId(), PERSON_UPDATE);

    // Assert
    assertThat(result).isEqualTo(PERSON_WITH_ID_1);
  }

  @Test
  public void testUpdatePersonNotFoundException() {
    // Arrange
    when(personRepository.findById(PERSON_UPDATE.getId())).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(PersonNotFoundException.class,
        () -> personService.updatePerson(PERSON_UPDATE.getId(), PERSON_UPDATE));
  }

  @Test
  public void testDeletePerson() throws PersonNotFoundException {
    // Arrange
    when(personRepository.findById(PERSON_WITH_ID_1.getId())).thenReturn(Optional.of(PERSON_WITH_ID_1));

    // Act
    Person result = personService.deletePerson(PERSON_WITH_ID_1.getId());

    // Assert
    assertEquals(PERSON_WITH_ID_1, result);
    Mockito.verify(personRepository).delete(PERSON_WITH_ID_1);
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
