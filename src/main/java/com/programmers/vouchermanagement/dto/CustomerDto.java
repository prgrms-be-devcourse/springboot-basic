package com.programmers.vouchermanagement.dto;

import com.programmers.vouchermanagement.domain.customer.Customer;

import java.time.LocalDateTime;
import java.util.UUID;

public record CustomerDto(UUID id, String name, LocalDateTime createdAt, boolean isBanned) {

    public static CustomerDto of(String name) {
        return new CustomerDto(null, name, null, false);
    }

    public static CustomerDto from(Customer customer) {
        return new CustomerDto(customer.getId(), customer.getName(), customer.getCreatedAt(), customer.isBanned());
    }
}
