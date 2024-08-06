package com.rental.controller.dto.customer;

import com.rental.entity.Customer;
import java.util.UUID;

public record CustomerDto(
        UUID id,
        String fullName,
        String email,
        String phoneNumber
) {
    public static CustomerDto fromEntity(Customer customer) {
        return new CustomerDto(
                customer.getId(),
                customer.getFullName(),
                customer.getEmail(),
                customer.getPhoneNumber()
        );
    }
}
