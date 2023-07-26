package com.programmers.voucher.testutil;

import com.programmers.voucher.domain.customer.domain.Customer;
import com.programmers.voucher.domain.customer.dto.CustomerDto;

import java.util.UUID;

public class CustomerTestUtil {
    private static final String DEFAULT_NAME = "customer";

    public static CustomerDto createCustomerDto(String email) {
        Customer customer = new Customer(UUID.randomUUID(), email, DEFAULT_NAME);
        return CustomerDto.from(customer);
    }

    public static CustomerDto createBannedCustomerDto(String email, boolean banned) {
        Customer customer = new Customer(UUID.randomUUID(), email, DEFAULT_NAME);
        if(banned) {
            customer.update(DEFAULT_NAME, true);
        }
        return CustomerDto.from(customer);
    }
}
