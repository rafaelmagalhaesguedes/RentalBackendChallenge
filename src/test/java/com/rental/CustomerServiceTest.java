package com.rental;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;

import com.rental.entity.Customer;
import com.rental.repository.CustomerRepository;
import com.rental.service.CustomerService;
import com.rental.service.exception.AccessoryNotFoundException;
import com.rental.service.exception.CustomerExistingException;
import com.rental.service.exception.CustomerNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
public class CustomerServiceTest {

  @Autowired
  CustomerService customerService;

  @MockBean
  CustomerRepository customerRepository;

  @Test
  public void testCustomerRetrievalById() throws CustomerNotFoundException {
    UUID id = UUID.randomUUID();
    Customer customer = new Customer();
    customer.setId(id);
    customer.setName("Rafa Guedes");
    customer.setEmail("rafa@gmail.com");

    Mockito.when(customerRepository.findById(eq(id)))
        .thenReturn(Optional.of(customer));

    Customer customerFromDb = customerService.getCustomerById(id);

    Mockito.verify(customerRepository).findById(id);

    assertEquals(customerFromDb.getId(), customer.getId());
    assertEquals(customerFromDb.getName(), customer.getName());
    assertEquals(customerFromDb.getEmail(), customer.getEmail());
  }

  @Test
  public void testGetAllCustomers() {

    // Arrange
    UUID customer1Id = UUID.randomUUID();
    UUID customer2Id = UUID.randomUUID();

    Customer customer1 = new Customer();
    customer1.setId(customer1Id);
    customer1.setName("Rafa Guedes");
    customer1.setEmail("rafa@email.com");

    Customer customer2 = new Customer();
    customer2.setId(customer2Id);
    customer2.setName("Jairo Rodrigues");
    customer2.setName("jairo@email.com");

    List<Customer> customers = Arrays.asList(customer1, customer2);

    Page<Customer> page = new PageImpl<>(customers);
    Pageable pageable = PageRequest.of(0, 2);

    // Act
    Mockito.when(customerRepository.findAll(pageable)).thenReturn(page);

    List<Customer> getAllCustomer = customerService.getAllCustomers(0, 2);

    Mockito.verify(customerRepository).findAll(pageable);

    // Assert
    assertEquals(2, getAllCustomer.size());
    assertEquals(customer1.getId(), customers.get(0).getId());
    assertEquals(customer1.getName(), customers.get(0).getName());
    assertEquals(customer1.getEmail(), customers.get(0).getEmail());

    assertEquals(customer2.getId(), customers.get(1).getId());
    assertEquals(customer2.getName(), customers.get(1).getName());
    assertEquals(customer2.getEmail(), customers.get(1).getEmail());
  }

  @Test
  public void testCreateCustomer() throws CustomerExistingException {

    // Arrange
    Customer customer = new Customer();
    customer.setId(UUID.randomUUID());
    customer.setName("Rafa Guedes");
    customer.setEmail("rafa@email.com");

    // Act
    Mockito.when(customerRepository.save(customer)).thenReturn(customer);

    Customer newCustomer = customerService.createCustomer(customer);

    Mockito.verify(customerRepository).save(customer);

    // Assert
    assertEquals(newCustomer.getId(), customer.getId());
    assertEquals(newCustomer.getName(), customer.getName());
    assertEquals(newCustomer.getEmail(), customer.getEmail());
  }

  @Test
  public void testUpdateAccessory() throws CustomerNotFoundException {

    // Arrange
    UUID id = UUID.randomUUID();

    Customer existingCustomer = new Customer();
    existingCustomer.setId(id);
    existingCustomer.setName("Rafa Guedes");
    existingCustomer.setEmail("rafa@email.com");

    Customer updatedCustomer= new Customer();
    updatedCustomer.setName("Rafael Magalhães Guedes");
    updatedCustomer.setEmail("rafaelmguedes@email.com");

    Mockito.when(customerRepository.findById(id)).thenReturn(Optional.of(existingCustomer));
    Mockito.when(customerRepository.save(existingCustomer)).thenReturn(existingCustomer);

    // Act
    Customer result = customerService.updateCustomer(id, updatedCustomer);

    // Assert
    assertEquals(updatedCustomer.getName(), result.getName());
    assertEquals(updatedCustomer.getEmail(), result.getEmail());
  }

  @Test
  public void testUpdateAccessoryNotFoundException() {

    // Arrange
    UUID id = UUID.randomUUID();

    Customer updatedCustomer = new Customer();
    updatedCustomer.setName("Rafael Guedes");
    updatedCustomer.setEmail("rafa@email.com");

    Mockito.when(customerRepository.findById(id))
        .thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(CustomerNotFoundException.class,
        () -> customerService.updateCustomer(id, updatedCustomer));
  }

  @Test
  public void testDeleteAccessory() throws CustomerNotFoundException {

    // Arrange
    UUID id = UUID.randomUUID();
    Customer existingCustomer = new Customer();
    existingCustomer.setId(id);
    existingCustomer.setName("Rafa Guedes");
    existingCustomer.setEmail("rafael@email.com");

    Mockito.when(customerRepository.findById(id))
        .thenReturn(Optional.of(existingCustomer));

    // Act
    Customer result = customerService.deleteCustomer(id);

    // Assert
    assertEquals(existingCustomer, result);
    Mockito.verify(customerRepository).delete(existingCustomer);
  }

  @Test
  public void testDeleteAccessoryNotFoundException() {

    // Arrange
    UUID id = UUID.randomUUID();

    Mockito.when(customerRepository.findById(id))
        .thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(CustomerNotFoundException.class,
        () -> customerService.deleteCustomer(id));
  }
}
