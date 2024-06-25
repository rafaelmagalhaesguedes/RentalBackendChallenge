package com.rental.repository;

import com.rental.entity.Person;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository <Person, UUID> {
  Optional<Person> findByUsername(String username);
  boolean existsByEmail(String email);
}
