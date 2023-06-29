package com.dev.voucherproject.model.customer;

import java.util.UUID;

public class CustomerDto {
    private UUID customerId;
    private String name;

    private CustomerDto(UUID customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public static CustomerDto fromEntity(Customer customer) {
        return new CustomerDto(customer.getCustomerId(), customer.getName());
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}
