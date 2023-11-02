package com.programmers.vouchermanagement.customer.dto;

import com.programmers.vouchermanagement.customer.domain.Customer;

import java.util.UUID;

public record CustomerDto(UUID id, String name, boolean isBlack) {
    public static CustomerDto from(Customer customer) {
        return new CustomerDto(customer.getId(), customer.getName(), customer.isBlack());
    }
}