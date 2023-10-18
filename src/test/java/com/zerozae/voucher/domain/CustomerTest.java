package com.zerozae.voucher.domain;

import com.zerozae.voucher.domain.customer.Customer;
import com.zerozae.voucher.domain.customer.CustomerType;
import com.zerozae.voucher.exception.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    @Test
    @DisplayName("회원 생성 성공 테스트")
    void createCustomerSuccessTest(){
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
    @DisplayName("회원 생성 실패 테스트")
    void createCustomerFailedTest(){
        // Given
        UUID customerId = UUID.randomUUID();
        String username = "";
        CustomerType customerType = CustomerType.BLACKLIST;

        // When

        // Then
        assertThrows(ErrorMessage.class, () -> {
            new Customer(customerId, username, customerType);
        });
    }
}
