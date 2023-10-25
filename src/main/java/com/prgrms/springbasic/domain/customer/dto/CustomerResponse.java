package com.prgrms.springbasic.domain.customer.dto;

import com.prgrms.springbasic.domain.customer.entity.Customer;

import java.util.UUID;

public record CustomerResponse(
        UUID customerId, String name, String email
) {
    public static CustomerResponse from(Customer customer) {
        return new CustomerResponse(
                customer.getCustomerId(),
                customer.getName(),
                customer.getEmail()
        );
    }
}
