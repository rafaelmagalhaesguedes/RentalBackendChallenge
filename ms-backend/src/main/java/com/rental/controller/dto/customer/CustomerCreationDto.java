package com.rental.controller.dto.customer;

import com.rental.entity.Customer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record CustomerCreationDto(
        @NotBlank(message = "Name is mandatory")
        String name,

        @NotBlank(message = "Email is mandatory")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Phone number is mandatory")
        String phoneNumber,

        @CPF
        @NotBlank(message = "CPF number is mandatory")
        String documentNumber
) {
    public Customer toEntity() {
        return new Customer(name, email, phoneNumber, documentNumber);
    }
}
