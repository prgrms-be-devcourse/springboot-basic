package com.voucher.vouchermanagement.dto.customer;

import com.voucher.vouchermanagement.model.customer.Customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class CustomerJoinResponse {
    private final UUID id;
    private final String name;
    private final String email;
    private final LocalDateTime createdAt;

    public CustomerJoinResponse(UUID id, String name, String email, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public static CustomerJoinResponse of(Customer customer) {
        return new CustomerJoinResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getCreatedAt());
    }
}
