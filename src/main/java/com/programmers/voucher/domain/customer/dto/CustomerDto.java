package com.programmers.voucher.domain.customer.dto;

import java.util.UUID;

public class CustomerDto {
    private final UUID customerId;
    private final String email;
    private final String name;

    public CustomerDto(UUID customerId, String email, String name) {
        this.customerId = customerId;
        this.email = email;
        this.name = name;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
