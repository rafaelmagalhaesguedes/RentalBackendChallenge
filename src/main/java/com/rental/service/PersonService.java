package com.rental.service;

import com.rental.entity.Person;
import com.rental.repository.PersonRepository;
import com.rental.service.exception.CustomerExistingException;
import com.rental.service.exception.CustomerNotFoundException;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PersonService implements UserDetailsService {

  private final PersonRepository personRepository;

  @Autowired
  public PersonService(PersonRepository personRepository) {
    this.personRepository = personRepository;
  }

  public Person getPersonById(UUID id) throws CustomerNotFoundException {
    return personRepository.findById(id)
        .orElseThrow(CustomerNotFoundException::new);
  }

  public List<Person> getAllPersons(int pageNumber, int pageSize) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    Page<Person> page = personRepository.findAll(pageable);

    return page.toList();
  }

  public Person createPerson(Person person) throws CustomerExistingException {
    if (personRepository.existsByEmail(person.getEmail())) {
      throw new CustomerExistingException();
    }

    return personRepository.save(person);
  }

  public Person updateCustomer(UUID customerId, Person person) throws CustomerNotFoundException {
    Person personFromDb = getPersonById(customerId);

    personFromDb.setName(person.getName());
    personFromDb.setEmail(person.getEmail());

    return personRepository.save(personFromDb);
  }

  public Person deleteCustomer(UUID id) throws CustomerNotFoundException {
    Person person = getPersonById(id);

    personRepository.delete(person);
    return person;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return personRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
  }
}
