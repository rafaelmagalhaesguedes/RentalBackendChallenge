package com.rental.controller.dto.person;

import com.rental.entity.Person;
import java.util.UUID;

/**
 * The type Person dto.
 */
public record PersonDto(
    UUID id,
    String fullName,
    String username,
    String email
) {

  /**
   * From entity person dto.
   *
   * @param person the person
   * @return the person dto
   */
  public static PersonDto fromEntity(Person person) {
    return new PersonDto(
        person.getId(),
        person.getFullName(),
        person.getUsername(),
        person.getEmail()
    );
  }
}
