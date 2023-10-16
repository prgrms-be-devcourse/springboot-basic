package com.zerozae.voucher.service;

import com.zerozae.voucher.common.message.MessageConverter;
import com.zerozae.voucher.domain.customer.CustomerType;
import com.zerozae.voucher.dto.customer.CustomerRequest;
import com.zerozae.voucher.dto.customer.CustomerResponse;
import com.zerozae.voucher.exception.ExceptionHandler;
import com.zerozae.voucher.mock.MockCustomerRepository;
import com.zerozae.voucher.mock.MockMessageSource;
import com.zerozae.voucher.repository.customer.CustomerRepository;
import com.zerozae.voucher.service.customer.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {
    CustomerService customerService;
    CustomerRepository customerRepository;
    MessageConverter messageConverter;

    @BeforeEach
    void setUp(){
        customerRepository = new MockCustomerRepository();
        customerService = new CustomerService(customerRepository);
        messageConverter = new MessageConverter(new MockMessageSource());
    }

    @Test
    @DisplayName("회원 등록 메서드 호출 테스트")
    void callCreateCustomerTest(){
        // Given

        CustomerRequest customerRequest = new CustomerRequest("영재", CustomerType.BLACKLIST);
        // When
        CustomerResponse customer = customerService.createCustomer(customerRequest);

        // Then
        assertEquals("영재", customer.getCustomerName());
    }

    @Test
    @DisplayName("회원 전체 조회 메서드 호출 테스트")
    void callFindAllBlacklistTest(){
        // Given
        CustomerRequest customer1 = new CustomerRequest("zero", CustomerType.BLACKLIST);
        CustomerRequest customer2 = new CustomerRequest("one", CustomerType.BLACKLIST);

        CustomerResponse zero = customerService.createCustomer(customer1);
        CustomerResponse one = customerService.createCustomer(customer2);

        // When
        List<CustomerResponse> customers = customerService.findAllCustomers();

        // Then
        assertEquals(customers.size() , 2);
        assertTrue(customers.contains(zero));
        assertTrue(customers.contains(one));
    }
    @Test
    @DisplayName("회원 중복 검사 테스트")
    void validateDuplicateCustomerTest(){
        // Given
        CustomerRequest customer = new CustomerRequest("zero", CustomerType.BLACKLIST);
        customerService.createCustomer(customer);

        // When

        // Then
        assertThrows(ExceptionHandler.class, () -> {
            customerService.createCustomer(new CustomerRequest("zero", CustomerType.NORMAL));
        });
    }
}
