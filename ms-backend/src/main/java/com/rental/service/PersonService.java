package com.rental.service;

import com.rental.entity.Person;
import com.rental.producer.PersonProducer;
import com.rental.repository.PersonRepository;
import com.rental.security.Role;
import com.rental.service.exception.PersonExistingException;
import com.rental.service.exception.PersonNotFoundException;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonService implements UserDetailsService {

  private final PersonRepository personRepository;
  private final PersonProducer personProducer;

  @Autowired
  public PersonService(PersonRepository personRepository, PersonProducer personProducer) {
    this.personRepository = personRepository;
    this.personProducer = personProducer;
  }

  public Person getPersonById(UUID id) throws PersonNotFoundException {
    return personRepository.findById(id)
        .orElseThrow(PersonNotFoundException::new);
  }

  public List<Person> getAllPersons(int pageNumber, int pageSize) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    Page<Person> page = personRepository.findAll(pageable);

    return page.toList();
  }

  public Person createPerson(Person person) throws PersonExistingException {
    if (personRepository.existsByEmail(person.getEmail())) {
      throw new PersonExistingException();
    }

    String hashPassword = new BCryptPasswordEncoder().encode(person.getPassword());

    person.setPassword(hashPassword);
    person.setRole(Role.USER);

    personProducer.publishMessageEmail(person);
    return personRepository.save(person);
  }

  public Person updateCustomer(UUID id, Person person) throws PersonNotFoundException {
    Person personFromDb = getPersonById(id);

    personFromDb.setName(person.getName());
    personFromDb.setEmail(person.getEmail());

    return personRepository.save(personFromDb);
  }

  public Person deleteCustomer(UUID id) throws PersonNotFoundException {
    Person person = getPersonById(id);

    personRepository.delete(person);
    return person;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return personRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
  }
}
