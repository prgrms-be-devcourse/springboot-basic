package com.prgms.management.customer.dto;

import com.prgms.management.customer.model.Customer;

import java.sql.Timestamp;
import java.util.UUID;

public record CustomerResponse(
    UUID id,
    String email,
    Timestamp lastLoginAt,
    Timestamp createdAt,
    String name,
    String type
) {
    public static CustomerResponse of(Customer customer) {
        return new CustomerResponse(
            customer.getId(),
            customer.getEmail(),
            customer.getLastLoginAt(),
            customer.getCreatedAt(),
            customer.getName(),
            customer.getType().toString()
        );
    }
}
