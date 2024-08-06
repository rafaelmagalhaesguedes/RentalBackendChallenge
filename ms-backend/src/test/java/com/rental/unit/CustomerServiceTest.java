package com.rental.unit;

import static com.rental.mock.CustomerMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rental.entity.Customer;
import com.rental.repository.CustomerRepository;
import com.rental.service.CustomerService;
import com.rental.service.exception.CustomerNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * Unit Tests for CustomerService Class
 * *
 * Created by Rafa Guedes
 * */
@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    @Test
    public void testCustomerRetrievalById() throws CustomerNotFoundException {
        // Arrange
        when(customerRepository.findById(eq(CUSTOMER_01.getId())))
                .thenReturn(Optional.of(CUSTOMER_01));

        // Act
        Customer getCustomer = customerService.getCustomerById(CUSTOMER_01.getId());

        // Assert
        assertThat(getCustomer).isEqualTo(CUSTOMER_01);
    }

    @Test
    public void testCustomerRetrievalByIdNotFound() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(customerRepository.findById(eq(id))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CustomerNotFoundException.class, () -> {
            customerService.getCustomerById(id);
        });
    }

    @Test
    public void testGetAllCustomers() {
        // Arrange
        List<Customer> customers = Arrays.asList(CUSTOMER_01, CUSTOMER_02);
        Page<Customer> page = new PageImpl<>(customers);
        Pageable pageable = PageRequest.of(0, 2);

        // Act
        when(customerRepository.findAll(pageable)).thenReturn(page);
        List<Customer> getAllCustomers = customerService.getAllCustomers(0, 2);
        verify(customerRepository).findAll(pageable);

        // Assert
        assertEquals(2, getAllCustomers.size());
        assertThat(getAllCustomers).isEqualTo(customers);
    }

    @Test
    public void testGetAllCustomersEmpty() {
        // Arrange
        Page<Customer> page = new PageImpl<>(List.of());
        Pageable pageable = PageRequest.of(0, 2);

        // Act
        when(customerRepository.findAll(pageable)).thenReturn(page);
        List<Customer> getAllCustomers = customerService.getAllCustomers(0, 2);
        verify(customerRepository).findAll(pageable);

        // Assert
        assertThat(getAllCustomers).isEmpty();
    }

    @Test
    public void testCreateCustomer() {
        // Arrange
        when(customerRepository.save(CUSTOMER_CREATION)).thenReturn(CUSTOMER_CREATION);

        // Act
        Customer newCustomer = customerService.createCustomer(CUSTOMER_CREATION);
        verify(customerRepository).save(CUSTOMER_CREATION);

        // Assert
        assertThat(newCustomer).isEqualTo(CUSTOMER_CREATION);
    }

    @Test
    public void testCreateCustomerFail() {
        // Arrange
        when(customerRepository.save(CUSTOMER_CREATION)).thenThrow(new RuntimeException("Unexpected error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            customerService.createCustomer(CUSTOMER_CREATION);
        });

        assertThat(exception.getMessage()).isEqualTo("Unexpected error");
    }

    @Test
    public void testUpdateCustomer() throws CustomerNotFoundException {
        // Arrange
        when(customerRepository.findById(CUSTOMER_01.getId())).thenReturn(Optional.of(CUSTOMER_01));
        when(customerRepository.save(CUSTOMER_01)).thenReturn(CUSTOMER_01);

        // Act
        Customer result = customerService.updateCustomer(CUSTOMER_UPDATED, CUSTOMER_01.getId());

        // Assert
        assertThat(result).isEqualTo(CUSTOMER_01);
    }

    @Test
    public void testUpdateCustomerNotFoundException() {
        // Arrange
        when(customerRepository.findById(CUSTOMER_01.getId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CustomerNotFoundException.class, () -> {
            customerService.updateCustomer(CUSTOMER_UPDATED, CUSTOMER_01.getId());
        });
    }

    @Test
    public void testDeleteCustomer() throws CustomerNotFoundException {
        // Arrange
        when(customerRepository.findById(CUSTOMER_01.getId())).thenReturn(Optional.of(CUSTOMER_01));

        // Act
        Customer result = customerService.deleteCustomer(CUSTOMER_01.getId());

        // Assert
        assertEquals(CUSTOMER_01, result);
        verify(customerRepository).delete(CUSTOMER_01);
    }

    @Test
    public void testDeleteCustomerNotFoundException() {
        // Arrange
        UUID id = UUID.randomUUID();

        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CustomerNotFoundException.class, () -> {
            customerService.deleteCustomer(id);
        });
    }
}
