package com.programmers.voucher.domain.customer.dto;

import com.programmers.voucher.domain.customer.domain.Customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class CustomerDto {
    private final UUID customerId;
    private final String email;
    private final String name;
    private final boolean banned;
    private final LocalDateTime createdAt;

    private CustomerDto(UUID customerId, String email, String name, boolean banned, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.email = email;
        this.name = name;
        this.banned = banned;
        this.createdAt = createdAt;
    }

    public static CustomerDto from(Customer customer) {
        UUID customerId = customer.getCustomerId();
        String email = customer.getEmail();
        String name = customer.getName();
        boolean banned = customer.isBanned();
        LocalDateTime createdAt = customer.getCreatedAt();
        return new CustomerDto(customerId, email, name, banned, createdAt);
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
