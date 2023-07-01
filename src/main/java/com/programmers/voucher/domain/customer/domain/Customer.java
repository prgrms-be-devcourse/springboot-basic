package com.programmers.voucher.domain.customer.domain;

import com.programmers.voucher.domain.customer.dto.CustomerDto;

import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final String name;

    public Customer(UUID customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public CustomerDto toDto() {
        return new CustomerDto(customerId, name);
    }

    public String fullInfoString() {
        return "customerId: " + customerId + ", name: " + name;
    }

    public UUID getCustomerId() {
        return customerId;
    }
}
