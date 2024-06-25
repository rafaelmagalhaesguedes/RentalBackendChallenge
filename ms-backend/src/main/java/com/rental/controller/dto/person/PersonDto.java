package com.rental.controller.dto.person;

import com.rental.entity.Person;
import java.util.UUID;

public record PersonDto(
    UUID id,
    String username,
    String email
) {
  public static PersonDto fromEntity(Person person) {
    return new PersonDto(
        person.getId(),
        person.getUsername(),
        person.getEmail()
    );
  }
}
