package com.programmers.vouchermanagement.customer.controller.dto;

import com.programmers.vouchermanagement.customer.domain.Customer;

import java.util.UUID;

public record CustomerResponse(UUID id, String name, boolean isBlack) {
    public static CustomerResponse from(Customer customer) {
        return new CustomerResponse(customer.getId(), customer.getName(), customer.isBlack());
    }
}