package com.programmers.vouchermanagement.customer.dto;

import java.util.UUID;

import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.customer.domain.CustomerType;

public record CustomerResponse(UUID customerId, String name, CustomerType customerType) {
    public static CustomerResponse from(Customer customer) {
        return new CustomerResponse(customer.getCustomerId(), customer.getName(), customer.getCustomerType());
    }
}
