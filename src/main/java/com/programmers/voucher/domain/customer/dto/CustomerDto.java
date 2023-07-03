package com.programmers.voucher.domain.customer.dto;

import java.util.UUID;

public class CustomerDto {
    private final UUID customerId;
    private final String email;
    private final String name;
    private final boolean banned;

    public CustomerDto(UUID customerId, String email, String name, boolean banned) {
        this.customerId = customerId;
        this.email = email;
        this.name = name;
        this.banned = banned;
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

    public boolean isBanned() {
        return banned;
    }
}
