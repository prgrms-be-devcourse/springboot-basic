package com.programmers.customer.dto;

import java.util.Objects;
import java.util.UUID;

public record CustomerRequestDto (UUID customerId, String name){

    private static final String CUSTOMER_NULL_MESSAGE = "[ERROR] 유효하지 않은 Customer Id 입니다. (null)";

    public CustomerRequestDto {
        validateCustomerId(customerId);
    }

    private void validateCustomerId(UUID customerId) {
        if (customerId == null) throw new IllegalArgumentException(CUSTOMER_NULL_MESSAGE);
    }
}
