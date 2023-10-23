package com.programmers.springbootbasic.domain.customer.service;

import com.programmers.springbootbasic.domain.customer.dto.CustomerRequestDto;
import com.programmers.springbootbasic.domain.customer.entity.Customer;
import com.programmers.springbootbasic.domain.customer.exception.ErrorMsg;
import com.programmers.springbootbasic.domain.customer.repository.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Customer Service Test")
class CustomerServiceTest {
    private static final String EMAIL = "test@gmail.com";
    private static final String NAME = "test";
    private static final UUID CUSTOMER_ID = UUID.randomUUID();
    @InjectMocks
    private CustomerService customerService;
    @Mock
    private CustomerRepository customerRepository;

    @Test
    void testCreateCustomerSuccess() {
        // Arrange
        CustomerRequestDto customerRequestDto = CustomerRequestDto.builder()
                .email(EMAIL)
                .name(NAME)
                .build();
        when(customerRepository.save(any(Customer.class))).thenReturn(Customer.builder().email(EMAIL).name(NAME).isBlacklist(false).build());
        when(customerRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());
        // Act
        Customer actualResult = customerService.createCustomer(customerRequestDto);
        // Assert
        assertEquals(EMAIL, actualResult.getEmail());
        assertEquals(NAME, actualResult.getName());
    }

    @DisplayName("Test createCustomer Failed when same Email already exist")
    @Test
    void testCreateCustomerFailed() {
        CustomerRequestDto customerRequestDto = CustomerRequestDto.builder()
                .email(EMAIL)
                .name(NAME)
                .build();
        Customer expectedCustomer = Customer.builder().email(EMAIL).name(NAME).isBlacklist(false).build();
        when(customerRepository.findByEmail(any(String.class))).thenReturn(Optional.of(expectedCustomer));
        // Act & Assert
        Throwable actualResult = assertThrows(IllegalArgumentException.class, () -> customerService.createCustomer(customerRequestDto));
        assertEquals(ErrorMsg.CUSTOMER_ALREADY_EXIST.getMessage(), actualResult.getMessage());
    }

    @Test
    void testAddCustomerInBlacklistSuccess() {
        // Arrange
        CustomerRequestDto customerRequestDto = CustomerRequestDto.builder()
                .email(EMAIL)
                .build();
        Customer expectedCustomer = Customer.builder()
                .customerId(CUSTOMER_ID)
                .email(EMAIL)
                .name(NAME)
                .isBlacklist(false)
                .build();

        when(customerRepository.findByEmail(EMAIL)).thenReturn(Optional.of(expectedCustomer));
        // Act
        customerService.addCustomerInBlacklist(customerRequestDto);
        // Assert
        assertTrue(expectedCustomer.isBlacklist());
    }

    @DisplayName("Test addCustomerInBlacklist Fail: Customer not Found")
    @Test
    void testAddCustomerInBlacklistFailWhenCustomerNotFound() {
        // Arrange
        CustomerRequestDto customerRequestDto = CustomerRequestDto.builder()
                .email(EMAIL)
                .build();
        when(customerRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());
        // Act & Assert
        Throwable actualResult = assertThrows(IllegalArgumentException.class, () -> customerService.addCustomerInBlacklist(customerRequestDto));
        assertEquals(ErrorMsg.CUSTOMER_NOT_FOUND.getMessage(), actualResult.getMessage());
    }

    @DisplayName("Test addCustomerInBlacklist Fail: Customer already in Blacklist")
    @Test
    void testAddCustomerInBlacklistFailWhenCustomerAlreadyInBlacklist() {
        // Arrange
        CustomerRequestDto customerRequestDto = CustomerRequestDto.builder()
                .email(EMAIL)
                .build();
        Customer expectedCustomer = Customer.builder()
                .customerId(CUSTOMER_ID)
                .email(EMAIL)
                .name(NAME)
                .isBlacklist(true)
                .build();
        when(customerRepository.findByEmail(EMAIL)).thenReturn(Optional.of(expectedCustomer));
        // Act & Assert
        Throwable actualResult = assertThrows(RuntimeException.class, () -> customerService.addCustomerInBlacklist(customerRequestDto));
        assertEquals(ErrorMsg.ALREADY_IN_BLACKLIST.getMessage(), actualResult.getMessage());
    }

    @Test
    void testRemoveCustomerFromBlacklistSuccess() {
        // Arrange
        CustomerRequestDto customerRequestDto = CustomerRequestDto.builder()
                .email(EMAIL)
                .build();
        Customer expectedCustomer = Customer.builder()
                .customerId(CUSTOMER_ID)
                .email(EMAIL)
                .name(NAME)
                .isBlacklist(true)
                .build();

        when(customerRepository.findByEmail(EMAIL)).thenReturn(Optional.of(expectedCustomer));
        // Act
        customerService.removeCustomerFromBlacklist(customerRequestDto);
        // Assert
        assertFalse(expectedCustomer.isBlacklist());
    }

    @DisplayName("Test removeCustomerFromBlacklist Fail: Customer not Found")
    @Test
    void testRemoveCustomerFromBlacklistFailWhenCustomerNotFound() {
        // Arrange
        CustomerRequestDto customerRequestDto = CustomerRequestDto.builder()
                .email(EMAIL)
                .build();
        when(customerRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());
        // Act & Assert
        Throwable actualResult = assertThrows(IllegalArgumentException.class, () -> customerService.removeCustomerFromBlacklist(customerRequestDto));
        assertEquals(ErrorMsg.CUSTOMER_NOT_FOUND.getMessage(), actualResult.getMessage());
    }

    @DisplayName("Test removeCustomerFromBlacklist Fail: Customer not in Blacklist")
    @Test
    void testRemoveCustomerFromBlacklistFailWhenCustomerAlreadyInBlacklist() {
        // Arrange
        CustomerRequestDto customerRequestDto = CustomerRequestDto.builder()
                .email(EMAIL)
                .build();
        Customer expectedCustomer = Customer.builder()
                .customerId(CUSTOMER_ID)
                .email(EMAIL)
                .name(NAME)
                .isBlacklist(false)
                .build();
        when(customerRepository.findByEmail(EMAIL)).thenReturn(Optional.of(expectedCustomer));
        // Act & Assert
        Throwable actualResult = assertThrows(RuntimeException.class, () -> customerService.removeCustomerFromBlacklist(customerRequestDto));
        assertEquals(ErrorMsg.NOT_IN_BLACKLIST.getMessage(), actualResult.getMessage());
    }

}