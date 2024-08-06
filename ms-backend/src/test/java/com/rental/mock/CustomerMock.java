package com.rental.mock;

import com.rental.entity.Customer;
import java.util.UUID;

/**
 * Mock data for Customer entity.
 *
 * Created by Rafa Guedes
 */
public class CustomerMock {

    // Example Customer instances for testing
    public static final Customer CUSTOMER_01 = new Customer(
            UUID.randomUUID(), // Sample UUID
            "John Doe",
            "john.doe@example.com",
            "12345678900", // Sample CPF
            "1234567890" // Sample phoneNumber
    );

    public static final Customer CUSTOMER_02 = new Customer(
            UUID.randomUUID(), // Sample UUID
            "Jane Doe",
            "jane.doe@example.com",
            "12345678900", // Sample CPF
            "1234567890"  // Sample phoneNumber
    );

    public static final Customer CUSTOMER_CREATION = new Customer(
            UUID.randomUUID(), // Sample UUID
            "New customer",
            "new.customer@example.com",
            "12345678900", // Sample CPF
            "1234567890"  // Sample phoneNumber
    );

    public static final Customer CUSTOMER_UPDATED = new Customer(
            UUID.randomUUID(), // Sample UUID
            "Update customer",
            "update.customer@example.com",
            "12345678900", // Sample CPF
            "1234567890" // Sample phoneNumber
    );
}
