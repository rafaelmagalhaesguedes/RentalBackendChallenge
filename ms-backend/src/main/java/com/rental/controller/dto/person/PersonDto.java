package com.rental.controller.dto.person;

import com.rental.entity.Person;
import java.util.UUID;

public record PersonDto(
    UUID id,
    String fullName,
    String username,
    String email
) {
  public static PersonDto fromEntity(Person person) {
    return new PersonDto(
        person.getId(),
        person.getFullName(),
        person.getUsername(),
        person.getEmail()
    );
  }
}
