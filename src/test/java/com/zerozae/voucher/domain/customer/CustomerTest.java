package com.zerozae.voucher.domain;

import com.zerozae.voucher.domain.customer.Customer;
import com.zerozae.voucher.domain.customer.CustomerType;
import com.zerozae.voucher.dto.customer.CustomerRequest;
import com.zerozae.voucher.exception.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    @DisplayName("회원 생성 성공 테스트")
    void createCustomer_Success_Test() {
        // Given
        UUID customerId = UUID.randomUUID();
        String username = "영재";
        CustomerType customerType = CustomerType.BLACKLIST;

        // When
        Customer createdCustomer = new Customer(customerId, username, customerType);

        // Then
        assertEquals(createdCustomer.getCustomerName(), username);
        assertEquals(createdCustomer.getCustomerId(), customerId);
    }


    @Test
    @DisplayName("회원 생성 실패 (빈 값 입력) 테스트")
    void createCustomer_emptyInput_Failed_Test() {
        // Given
        UUID customerId = UUID.randomUUID();
        String username = "";
        CustomerType customerType = CustomerType.BLACKLIST;

        // When & Then
        assertThrows(ErrorMessage.class, () -> {
            new Customer(customerId, username, customerType);
        });
    }

    @Test
    @DisplayName("회원 수정 성공 테스트")
    void customerUpdate_Success_Test() {
        // Given
        UUID customerId = UUID.randomUUID();
        String username = "영재";
        CustomerType customerType = CustomerType.BLACKLIST;
        Customer createdCustomer = new Customer(customerId, username, customerType);

        String newUsername = "zerozae";
        CustomerType newCustomerType = CustomerType.NORMAL;
        CustomerRequest customerRequest = new CustomerRequest(newUsername, newCustomerType);

        // When
        createdCustomer.updateCustomerInfo(customerRequest);

        // Then
        assertEquals(createdCustomer.getCustomerName(), newUsername);
        assertEquals(createdCustomer.getCustomerType() , newCustomerType);
    }

    @Test
    @DisplayName("회원 수정 실패 테스트(빈 값 입력)")
    void customerUpdate_emptyInput_Failed_Test() {
        // Given
        UUID customerId = UUID.randomUUID();
        String username = "영재";
        CustomerType customerType = CustomerType.BLACKLIST;
        Customer createdCustomer = new Customer(customerId, username, customerType);

        String newUsername = "";
        CustomerType newCustomerType = CustomerType.NORMAL;
        CustomerRequest customerRequest = new CustomerRequest(newUsername, newCustomerType);

        // When & Then
        assertThrows(ErrorMessage.class, () -> {
            createdCustomer.updateCustomerInfo(customerRequest);
        });
    }
}
