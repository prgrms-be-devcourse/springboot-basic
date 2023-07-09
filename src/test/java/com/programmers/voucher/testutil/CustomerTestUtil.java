package com.programmers.voucher.testutil;

import com.programmers.voucher.domain.customer.domain.Customer;
import com.programmers.voucher.domain.customer.dto.CustomerDto;

import java.util.UUID;

public class CustomerTestUtil {
    public static CustomerDto createCustomerDto(UUID customerId, String email, String name, boolean banned) {
        Customer customer = new Customer(customerId, email, name);
        if(banned) {
            customer.update(name, true);
        }
        return CustomerDto.from(customer);
    }
}
