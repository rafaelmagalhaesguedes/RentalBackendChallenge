package com.rental.controller.dto.customer;

import com.rental.entity.Customer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CustomerRequest(
        @NotBlank(message = "Name is mandatory")
        @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
        String name,

        @NotBlank(message = "Email is mandatory")
        @Email(message = "Email should be valid")
        String email,

        @NotBlank(message = "Password cannot be blank")
        @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
        String password,

        @NotBlank(message = "Phone number is mandatory")
        @Pattern(regexp = "\\d{11,13}", message = "Phone number should be between 11 and 13 digits")
        String phoneNumber,

        @NotBlank(message = "Document is mandatory")
        String document,

        @Pattern(regexp = "\\d{8}", message = "CEP should be exactly 8 digits")
        String cep
) {
    public Customer toEntity() {
        return Customer.builder()
                .name(name)
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .rawDocument(document)
                .build();
    }
}
