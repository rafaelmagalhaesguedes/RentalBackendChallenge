package com.rental.service;

import com.rental.entity.Customer;
import com.rental.repository.CustomerRepository;
import com.rental.service.exception.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * The type Customer service.
 */
@Service
public class CustomerService {

    // Inject repository
    private final CustomerRepository customerRepository;

    /**
     * Instantiates a new Customer service.
     *
     * @param customerRepository the customer repository
     */
    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Gets customer by id.
     *
     * @param id the id
     * @return the customer by id
     * @throws CustomerNotFoundException the customer not found exception
     */
    public Customer getCustomerById(UUID id) throws CustomerNotFoundException {
        return customerRepository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);
    }

    /**
     * Gets all customers.
     *
     * @param pageNumber the page number
     * @param pageSize   the page size
     * @return the all customers
     */
    public List<Customer> getAllCustomers(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Customer> page = customerRepository.findAll(pageable);
        return page.getContent();
    }

    /**
     * Create customer.
     *
     * @param customer the customer
     * @return the customer
     */
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    /**
     * Update customer.
     *
     * @param customer the customer
     * @param id       the id
     * @return the customer
     * @throws CustomerNotFoundException the customer not found exception
     */
    public Customer updateCustomer(Customer customer, UUID id) throws CustomerNotFoundException {
        Customer customerFromDb = getCustomerById(id);

        customerFromDb.setFullName(customer.getFullName());
        customerFromDb.setPhoneNumber(customer.getPhoneNumber());
        customerFromDb.setDocumentNumber(customer.getDocumentNumber());

        return customerRepository.save(customerFromDb);
    }

    /**
     * Delete customer.
     *
     * @param id the id
     * @return the customer
     * @throws CustomerNotFoundException the customer not found exception
     */
    public Customer deleteCustomer(UUID id) throws CustomerNotFoundException {
        Customer customer = getCustomerById(id);

        customerRepository.delete(customer);
        return customer;
    }
}
