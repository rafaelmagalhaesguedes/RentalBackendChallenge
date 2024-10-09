package com.rental.mock;

import com.rental.entity.Customer;
import com.rental.enums.Role;

import java.util.UUID;

/**
 * Mock data for Customer entity.
 * Created by Rafa Guedes
 */
public class CustomerMock {

    // Example Customer instances for testing
    public static final Customer CUSTOMER_01 = Customer.builder()
            .id(UUID.randomUUID()) // Sample UUID
            .name("John Doe")
            .email("john.doe@example.com")
            .password("password123") // Sample password
            .role(Role.USER) // Sample role
            .rawDocument("12345678900") // Sample CPF
            .phoneNumber("1234567890") // Sample phoneNumber
            .build();

    public static final Customer CUSTOMER_02 = Customer.builder()
            .id(UUID.randomUUID()) // Sample UUID
            .name("Jane Doe")
            .email("jane.doe@example.com")
            .password("password123") // Sample password
            .role(Role.USER) // Sample role
            .rawDocument("12345678900") // Sample CPF
            .phoneNumber("1234567890") // Sample phoneNumber
            .build();

    public static final Customer CUSTOMER_CREATION = Customer.builder()
            .id(UUID.randomUUID()) // Sample UUID
            .name("New customer")
            .email("new.customer@example.com")
            .password("password123") // Sample password
            .role(Role.USER) // Sample role
            .rawDocument("12345678900") // Sample CPF
            .phoneNumber("1234567890") // Sample phoneNumber
            .build();

    public static final Customer CUSTOMER_UPDATED = Customer.builder()
            .id(UUID.randomUUID()) // Sample UUID
            .name("Update customer")
            .email("update.customer@example.com")
            .password("password123") // Sample password
            .role(Role.USER) // Sample role
            .rawDocument("12345678900") // Sample CPF
            .phoneNumber("1234567890") // Sample phoneNumber
            .build();
}