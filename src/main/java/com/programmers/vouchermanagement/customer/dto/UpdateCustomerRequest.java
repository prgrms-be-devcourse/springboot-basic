package com.programmers.vouchermanagement.customer.dto;

import java.util.UUID;

import com.programmers.vouchermanagement.customer.domain.CustomerType;

public record UpdateCustomerRequest(UUID customerId, String name, CustomerType customerType) {
}
