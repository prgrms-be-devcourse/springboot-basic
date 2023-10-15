package com.prgrms.springbasic.domain.customer.dto;

import com.prgrms.springbasic.domain.customer.entity.Customer;

import java.util.UUID;

public record CustomerResponse(
        UUID customerId, String customerName
) {
    public static CustomerResponse from(Customer customer) {
        return new CustomerResponse(
                customer.getCustomerId(),
                customer.getName()
        );
    }

    @Override
    public String toString() {
        return """
                Customer Id : %s
                Customer Name : %s
                ------------------------------
                """.formatted(customerId, customerName);
    }
}
