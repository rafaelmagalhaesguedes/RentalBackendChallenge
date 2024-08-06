package com.rental.controller;

import com.rental.controller.dto.customer.CustomerCreationDto;
import com.rental.controller.dto.customer.CustomerDto;
import com.rental.entity.Customer;
import com.rental.service.CustomerService;
import com.rental.service.exception.CustomerNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing customers.
 */
@RestController
@RequestMapping("/customer")
@Validated
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Gets customer by id.
     *
     * @param id the id
     * @return the customer by id
     * @throws CustomerNotFoundException the customer not found exception
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get customer by ID", description = "Retrieve a specific customer by its ID")
    @ApiResponse(responseCode = "200", description = "Customer successfully retrieved")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    @Cacheable(value = "customerCache", key = "#id")
    public CustomerDto getCustomerById(@PathVariable UUID id) throws CustomerNotFoundException {
        return CustomerDto.fromEntity(
                customerService.getCustomerById(id)
        );
    }

    /**
     * Gets all customers with pagination.
     *
     * @param pageNumber the page number to retrieve (default is 0)
     * @param pageSize the number of items per page (default is 10)
     * @return a list of customer DTOs
     */
    @GetMapping
    @Operation(summary = "List all customers", description = "List all customers with pagination")
    @ApiResponse(responseCode = "200", description = "List of customers successfully retrieved")
    @Cacheable(value = "customersCache")
    public List<CustomerDto> getAllCustomers(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "20") int pageSize) {
        List<Customer> paginatedCustomers = customerService.getAllCustomers(pageNumber, pageSize);

        return paginatedCustomers.stream()
                .map(CustomerDto::fromEntity)
                .toList();
    }

    /**
     * Creates a new customer.
     *
     * @param customerCreationDto the DTO containing the customer creation data
     * @return the created customer DTO
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create customer", description = "Create a new customer")
    @ApiResponse(responseCode = "201", description = "Customer successfully created")
    @CacheEvict(value = "customersCache", allEntries = true)
    public CustomerDto createCustomer(@RequestBody @Valid CustomerCreationDto customerCreationDto) {
        return CustomerDto.fromEntity(
                customerService.createCustomer(customerCreationDto.toEntity())
        );
    }

    /**
     * Updates an existing customer.
     *
     * @param customerCreationDto the DTO containing the updated customer data
     * @param id the UUID of the customer to update
     * @return the updated customer DTO
     * @throws CustomerNotFoundException if the customer with the given ID is not found
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update customer", description = "Update an existing customer")
    @ApiResponse(responseCode = "200", description = "Customer successfully updated")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    @Caching(evict = {
            @CacheEvict(value = "customerCache", key = "#id"),
            @CacheEvict(value = "customersCache", allEntries = true)
    })
    public CustomerDto updateCustomer(@Valid @RequestBody CustomerCreationDto customerCreationDto, @PathVariable UUID id) throws CustomerNotFoundException {
        return CustomerDto.fromEntity(
                customerService.updateCustomer(customerCreationDto.toEntity(), id)
        );
    }

    /**
     * Deletes a customer by its ID.
     *
     * @param id the UUID of the customer to be deleted.
     * @return the deleted CustomerDto.
     * @throws CustomerNotFoundException if no customer with the given ID is found.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete customer", description = "Delete a customer by its ID")
    @ApiResponse(responseCode = "200", description = "Customer successfully deleted")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    @Caching(evict = {
            @CacheEvict(value = "customerCache", key = "#id"),
            @CacheEvict(value = "customersCache", allEntries = true)
    })
    public CustomerDto deleteCustomer(@PathVariable UUID id) throws CustomerNotFoundException {
        return CustomerDto.fromEntity(
                customerService.deleteCustomer(id)
        );
    }
}
