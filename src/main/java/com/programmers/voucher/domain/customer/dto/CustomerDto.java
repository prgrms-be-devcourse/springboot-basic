package com.programmers.voucher.domain.customer.dto;

import java.util.UUID;

public class CustomerDto {
    private final UUID customerId;
    private final String name;

    public CustomerDto(UUID customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}
