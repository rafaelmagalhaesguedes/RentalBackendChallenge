package com.rental.service;

import com.rental.entity.Customer;
import com.rental.repository.CustomerRepository;
import com.rental.service.exception.CustomerExistingException;
import com.rental.service.exception.CustomerNotFoundException;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

  private final CustomerRepository customerRepository;

  @Autowired
  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public Customer getCustomerById(UUID id) throws CustomerNotFoundException {
    return customerRepository.findById(id)
        .orElseThrow(CustomerNotFoundException::new);
  }

  public List<Customer> getAllCustomers(int pageNumber, int pageSize) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    Page<Customer> page = customerRepository.findAll(pageable);

    return page.toList();
  }

  public Customer createCustomer(Customer customer) throws CustomerExistingException {
    if (customerRepository.existsByEmail(customer.getEmail())) {
      throw new CustomerExistingException();
    }

    return customerRepository.save(customer);
  }

  public Customer updateCustomer(UUID customerId, Customer customer) throws CustomerNotFoundException {
    Customer customerFromDb = getCustomerById(customerId);

    customerFromDb.setName(customer.getName());
    customerFromDb.setEmail(customer.getEmail());

    return customerRepository.save(customerFromDb);
  }

  public Customer deleteCustomer(UUID id) throws CustomerNotFoundException {
    Customer customer = getCustomerById(id);

    customerRepository.delete(customer);
    return customer;
  }
}
