package com.rental.controller;

import com.rental.controller.dto.customer.CustomerCreationDto;
import com.rental.controller.dto.customer.CustomerDto;
import com.rental.controller.dto.customer.CustomerUpdateDto;
import com.rental.entity.Customer;
import com.rental.service.CustomerService;
import com.rental.service.exception.CustomerExistingException;
import com.rental.service.exception.CustomerNotFoundException;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
@Validated
public class CustomerController {

  private final CustomerService customerService;

  @Autowired
  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @GetMapping("/{id}")
  public CustomerDto getCustomerById(@PathVariable UUID id) throws CustomerNotFoundException {
    return CustomerDto.fromEntity(
        customerService.getCustomerById(id)
    );
  }

  @GetMapping
  public List<CustomerDto> getAllCustomers(
      @RequestParam(required = false, defaultValue = "0") int pageNumber,
      @RequestParam(required = false, defaultValue = "10") int pageSize
  ) {
    List<Customer> paginatedCustomers = customerService.getAllCustomers(pageNumber, pageSize);
    return paginatedCustomers.stream()
        .map(CustomerDto::fromEntity)
        .toList();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CustomerDto customerCreate(@RequestBody @Valid CustomerCreationDto customerCreationDto)
      throws CustomerNotFoundException, CustomerExistingException {
    return CustomerDto.fromEntity(
        customerService.createCustomer(customerCreationDto.toEntity())
    );
  }

  @PutMapping("/{id}")
  public CustomerDto customerUpdate(
      @PathVariable UUID id,
      @RequestBody @Valid CustomerUpdateDto customerUpdateDto
  ) throws CustomerNotFoundException {
    return CustomerDto.fromEntity(
        customerService.updateCustomer(id, customerUpdateDto.toEntity())
    );
  }

  @DeleteMapping("/{id}")
  public CustomerDto customerDelete(@PathVariable UUID id) throws CustomerNotFoundException {
    return CustomerDto.fromEntity(
        customerService.deleteCustomer(id)
    );
  }
}
