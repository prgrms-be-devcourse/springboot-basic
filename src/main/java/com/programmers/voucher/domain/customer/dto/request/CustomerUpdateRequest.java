package com.programmers.voucher.domain.customer.dto.request;

import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public class CustomerUpdateRequest {
    private static final String NAME_IN_RANGE = "Name 20 chars or less";
    private static final String NAME_PATTERN = "^[A-Za-z0-9]{1,20}";

    @Pattern(regexp = NAME_PATTERN, message = NAME_IN_RANGE)
    private final String name;
    private final UUID customerId;
    private final boolean banned;

    public CustomerUpdateRequest(UUID customerId, String name, boolean banned) {
        this.customerId = customerId;
        this.name = name;
        this.banned = banned;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public boolean isBanned() {
        return banned;
    }
}
