package com.rental.controller;

import com.rental.controller.dto.customer.CustomerRequest;
import com.rental.controller.dto.customer.CustomerResponse;
import com.rental.entity.Customer;
import com.rental.service.customer.ICustomerService;
import com.rental.service.exception.CustomerNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing customers.
 */
@RestController
@RequestMapping("/customer")
@Validated
public class CustomerController {

    private final ICustomerService customerService;

    @Autowired
    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Creates a new customer.
     *
     * @param request the DTO containing the customer creation data
     * @return the created customer DTO
     */
    @PostMapping
    @Operation(summary = "Create customer", description = "Create a new customer")
    @ApiResponse(responseCode = "201", description = "Customer successfully created")
    public ResponseEntity<Void> createCustomer(@RequestBody @Valid CustomerRequest request) {
        customerService.create(request.toEntity());

        return ResponseEntity.status(HttpStatus.CREATED).build();
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
    public CustomerResponse getCustomerById(@PathVariable UUID id) throws CustomerNotFoundException {
        var customer = customerService.getById(id);

        return CustomerResponse.fromEntity(customer);
    }

    /**
     * Updates an existing customer.
     *
     * @param request the DTO containing the updated customer data
     * @param id the UUID of the customer to update
     * @return the updated customer DTO
     * @throws CustomerNotFoundException if the customer with the given ID is not found
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update customer", description = "Update an existing customer")
    @ApiResponse(responseCode = "200", description = "Customer successfully updated")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    public ResponseEntity<Void> updateCustomer(
            @Valid @RequestBody CustomerRequest request,
            @PathVariable UUID id
    ) throws CustomerNotFoundException {
        customerService.update(request.toEntity(), id);

        return ResponseEntity.ok().build();
    }

    /**
     * Deletes a customer by its ID.
     *
     * @param id the UUID of the customer to be deleted.
     * @return the deleted CustomerRequest.
     * @throws CustomerNotFoundException if no customer with the given ID is found.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete customer", description = "Delete a customer by its ID")
    @ApiResponse(responseCode = "200", description = "Customer successfully deleted")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id) throws CustomerNotFoundException {
        customerService.delete(id);

        return ResponseEntity.ok().build();
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
    public List<CustomerResponse> getAllCustomers(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "20") int pageSize
    ) {
        List<Customer> customerList = customerService.getAll(pageNumber, pageSize);

        return customerList.stream()
                .map(CustomerResponse::fromEntity)
                .toList();
    }
}
