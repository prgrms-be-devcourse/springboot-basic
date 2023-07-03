package com.programmers.voucher.domain.customer.dto.request;

import java.util.UUID;

public class CustomerUpdateRequest {
    private final UUID customerId;
    private final String name;

    public CustomerUpdateRequest(UUID customerId, String name) {
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
