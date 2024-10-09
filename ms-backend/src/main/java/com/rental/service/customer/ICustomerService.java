package com.rental.service.customer;

import com.rental.entity.Customer;
import com.rental.service.exception.CustomerNotFoundException;

import java.util.List;
import java.util.UUID;

public interface ICustomerService {

    void create(Customer customer);

    Customer getById(UUID id) throws CustomerNotFoundException;

    void update(Customer customer, UUID id) throws CustomerNotFoundException;

    void delete(UUID id) throws CustomerNotFoundException;

    List<Customer> getAll(int pageNumber, int pageSize);
}
