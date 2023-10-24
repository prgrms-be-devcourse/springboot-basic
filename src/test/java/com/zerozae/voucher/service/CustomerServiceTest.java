package com.zerozae.voucher.service;

import com.zerozae.voucher.domain.customer.Customer;
import com.zerozae.voucher.domain.customer.CustomerType;
import com.zerozae.voucher.dto.customer.CustomerRequest;
import com.zerozae.voucher.dto.customer.CustomerResponse;
import com.zerozae.voucher.exception.ErrorMessage;
import com.zerozae.voucher.repository.customer.FileCustomerRepository;
import com.zerozae.voucher.service.customer.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {
    FileCustomerRepository fileCustomerRepository = mock(FileCustomerRepository.class);
    CustomerService customerService = new CustomerService(fileCustomerRepository);

    @Test
    @DisplayName("회원 등록 메서드 호출 테스트")
    void callCreateCustomerTest(){
        // Given
        CustomerRequest customerRequest = new CustomerRequest("영재", CustomerType.BLACKLIST);
        Customer savedCustomer = new Customer(UUID.randomUUID(), "영재", CustomerType.BLACKLIST);

        when(fileCustomerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        // When
        CustomerResponse customerResponse = customerService.createCustomer(customerRequest);

        // Then
        assertEquals("영재", customerResponse.getCustomerName());
        verify(fileCustomerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    @DisplayName("회원 전체 조회 메서드 호출 테스트")
    void callFindAllBlacklistTest(){
        // Given
        List<Customer> customerList = List.of(
                new Customer(UUID.randomUUID(),"고객1", CustomerType.NORMAL),
                new Customer(UUID.randomUUID(),"고객2", CustomerType.BLACKLIST)
        );

        when(fileCustomerRepository.findAll()).thenReturn(customerList);

        // When
        List<CustomerResponse> customers = customerService.findAllCustomers();

        // Then
        assertEquals(2, customers.size());
        assertTrue(customers.stream().anyMatch(c -> c.getCustomerName().equals("고객1")));
        assertTrue(customers.stream().anyMatch(c -> c.getCustomerName().equals("고객2")));

        verify(fileCustomerRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("회원 중복 검사 테스트")
    void validateDuplicateCustomerTest(){
        // Given
        CustomerRequest customerRequest = new CustomerRequest("고객1", CustomerType.NORMAL);

        when(fileCustomerRepository.findAll()).thenReturn(List.of(new Customer(UUID.randomUUID(),"고객1", CustomerType.NORMAL)));

        // When

        // Then
        assertThrows(ErrorMessage.class, () -> {
            customerService.createCustomer(customerRequest);
        });
    }
}
