package com.programmers.voucher.domain.customer.dto.request;

import java.util.UUID;

public class CustomerUpdateRequest {
    private UUID customerId;
    private String name;
    private boolean banned;

    public CustomerUpdateRequest() {
    }

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

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }
}
