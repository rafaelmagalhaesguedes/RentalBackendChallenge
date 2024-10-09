package com.rental.service.customer;

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
public class CustomerServiceImpl implements ICustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Create customer.
     *
     * @param customer the customer
     */
    @Override
    public void create(Customer customer) {
        customerRepository.save(customer);
    }

    /**
     * Gets customer by id.
     *
     * @param id the id
     * @return the customer by id
     * @throws CustomerNotFoundException the customer not found exception
     */
    @Override
    public Customer getById(UUID id) throws CustomerNotFoundException {
        return customerRepository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);
    }

    /**
     * Update customer.
     *
     * @param customer the customer
     * @param id       the id
     * @throws CustomerNotFoundException the customer not found exception
     */
    @Override
    public void update(Customer customer, UUID id) throws CustomerNotFoundException {
        Customer customerFromDb = getById(id);

        customerFromDb.setFullName(customer.getFullName());
        customerFromDb.setPhoneNumber(customer.getPhoneNumber());

        customerRepository.save(customerFromDb);
    }

    /**
     * Delete customer.
     *
     * @param id the id
     * @throws CustomerNotFoundException the customer not found exception
     */
    @Override
    public void delete(UUID id) throws CustomerNotFoundException {
        Customer customer = getById(id);

        customerRepository.delete(customer);
    }

    /**
     * Gets all customers.
     *
     * @param pageNumber the page number
     * @param pageSize   the page size
     * @return the all customers
     */
    @Override
    public List<Customer> getAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Customer> page = customerRepository.findAll(pageable);

        return page.getContent();
    }

}
