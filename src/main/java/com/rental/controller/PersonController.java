package com.rental.controller;

import com.rental.controller.dto.person.PersonCreationDto;
import com.rental.controller.dto.person.PersonDto;
import com.rental.controller.dto.person.PersonUpdateDto;
import com.rental.entity.Person;
import com.rental.service.PersonService;
import com.rental.service.exception.CustomerExistingException;
import com.rental.service.exception.CustomerNotFoundException;
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
 * The type Customer controller.
 */
@RestController
@RequestMapping("/customer")
@Validated
public class PersonController {

  private final PersonService personService;

  /**
   * Instantiates a new Customer controller.
   *
   * @param personService the customer service
   */
  @Autowired
  public PersonController(PersonService personService) {
    this.personService = personService;
  }

  /**
   * Gets customer by id.
   *
   * @param id the id
   * @return the customer by id
   * @throws CustomerNotFoundException the customer not found exception
   */
  @GetMapping("/{id}")
  public PersonDto getCustomerById(@PathVariable UUID id) throws CustomerNotFoundException {
    return PersonDto.fromEntity(
        personService.getPersonById(id)
    );
  }

  /**
   * Gets all customers.
   *
   * @param pageNumber the page number
   * @param pageSize   the page size
   * @return the all customers
   */
  @GetMapping
  @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
  public List<PersonDto> getAllCustomers(
      @RequestParam(required = false, defaultValue = "0") int pageNumber,
      @RequestParam(required = false, defaultValue = "10") int pageSize
  ) {
    List<Person> paginatedPeople = personService.getAllPersons(pageNumber, pageSize);
    return paginatedPeople.stream()
        .map(PersonDto::fromEntity)
        .toList();
  }

  /**
   * Customer create customer dto.
   *
   * @param personCreationDto the customer creation dto
   * @return the customer dto
   * @throws CustomerNotFoundException the customer not found exception
   * @throws CustomerExistingException the customer existing exception
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public PersonDto customerCreate(@RequestBody @Valid PersonCreationDto personCreationDto)
      throws CustomerNotFoundException, CustomerExistingException {
    return PersonDto.fromEntity(
        personService.createPerson(personCreationDto.toEntity())
    );
  }

  /**
   * Customer update customer dto.
   *
   * @param id                the id
   * @param personUpdateDto the customer update dto
   * @return the customer dto
   * @throws CustomerNotFoundException the customer not found exception
   */
  @PutMapping("/{id}")
  public PersonDto customerUpdate(
      @PathVariable UUID id,
      @RequestBody @Valid PersonUpdateDto personUpdateDto
  ) throws CustomerNotFoundException {
    return PersonDto.fromEntity(
        personService.updateCustomer(id, personUpdateDto.toEntity())
    );
  }

  /**
   * Customer delete customer dto.
   *
   * @param id the id
   * @return the customer dto
   * @throws CustomerNotFoundException the customer not found exception
   */
  @DeleteMapping("/{id}")
  public PersonDto customerDelete(@PathVariable UUID id) throws CustomerNotFoundException {
    return PersonDto.fromEntity(
        personService.deleteCustomer(id)
    );
  }
}
