package com.rental.controller;

import com.rental.controller.dto.person.PersonCreationDto;
import com.rental.controller.dto.person.PersonDto;
import com.rental.controller.dto.person.PersonUpdateDto;
import com.rental.entity.Person;
import com.rental.service.PersonService;
import com.rental.service.exception.PersonExistingException;
import com.rental.service.exception.PersonNotFoundException;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Persons controller.
 */
@RestController
@RequestMapping("/persons")
@Validated
public class PersonController {

  private final PersonService personService;

  /**
   * Instantiates a new Person controller.
   *
   * @param personService the person service
   */
  @Autowired
  public PersonController(PersonService personService) {
    this.personService = personService;
  }

  /**
   * Gets person by id.
   *
   * @param id the id
   * @return the person by id
   * @throws PersonNotFoundException the person not found exception
   */
  @GetMapping("/{id}")
  public PersonDto getPersonById(@PathVariable UUID id) throws PersonNotFoundException {
    return PersonDto.fromEntity(
        personService.getPersonById(id)
    );
  }

  /**
   * Gets all persons.
   *
   * @param pageNumber the page number
   * @param pageSize   the page size
   * @return the all persons
   */
  @GetMapping
  @PreAuthorize("hasAuthority('MANAGER')")
  public List<PersonDto> getAllPersons(
      @RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber,
      @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
  ) {
    List<Person> paginatedPeople = personService.getAllPersons(pageNumber, pageSize);
    return paginatedPeople.stream()
        .map(PersonDto::fromEntity)
        .toList();
  }

  /**
   * Customer create person dto.
   *
   * @param personCreationDto the customer creation dto
   * @return the person dto
   * @throws PersonExistingException the person existing exception
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public PersonDto personCreate(@RequestBody @Valid PersonCreationDto personCreationDto)
      throws PersonExistingException {
    return PersonDto.fromEntity(
        personService.createPerson(personCreationDto.toEntity())
    );
  }

  /**
   * Customer update person dto.
   *
   * @param id                the id
   * @param personUpdateDto the person update dto
   * @return the person dto
   * @throws PersonNotFoundException the person not found exception
   */
  @PutMapping("/{id}")
  public PersonDto personUpdate(
      @PathVariable UUID id,
      @RequestBody @Valid PersonUpdateDto personUpdateDto
  ) throws PersonNotFoundException {
    return PersonDto.fromEntity(
        personService.updatePerson(id, personUpdateDto.toEntity())
    );
  }

  /**
   * Customer delete person dto.
   *
   * @param id the id
   * @return the person dto
   * @throws PersonNotFoundException the person not found exception
   */
  @DeleteMapping("/{id}")
  public PersonDto personDelete(@PathVariable UUID id) throws PersonNotFoundException {
    return PersonDto.fromEntity(
        personService.deletePerson(id)
    );
  }
}
