package com.programmers.vouchermanagement.customer.dto;

import com.programmers.vouchermanagement.customer.domain.CustomerType;

import java.util.UUID;

public class CustomerResponseDto {

    private final UUID customerId;
    private final String name;
    private final CustomerType customerType;

    public CustomerResponseDto(UUID customerId, String name, CustomerType customerType) {
        this.customerId = customerId;
        this.name = name;
        this.customerType = customerType;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }
}
