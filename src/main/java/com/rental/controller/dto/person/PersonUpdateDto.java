package com.rental.controller.dto.person;

import com.rental.entity.Person;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PersonUpdateDto(
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 255, message = "Name must be less than or equal to 255 characters")
    String fullName,

    @Email(message = "Invalid email")
    @NotBlank(message = "Email cannot be blank")
    String email
) {
  public Person toEntity() {
    return new Person(fullName, email);
  }
}
