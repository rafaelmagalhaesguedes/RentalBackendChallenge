package com.rental.controller.dto.customer;

import com.rental.entity.Customer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CustomerUpdateDto(
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 255, message = "Name must be less than or equal to 255 characters")
    String name,

    @Email(message = "Invalid email")
    @NotBlank(message = "Email cannot be blank")
    String email
) {
  public Customer toEntity() {
    return new Customer(name, email);
  }
}
