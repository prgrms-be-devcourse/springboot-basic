package com.example.springbootbasic.domain.customer;

import java.util.Arrays;

import static com.example.springbootbasic.exception.CustomerStatusExceptionMessage.CUSTOMER_STATUS_FIND_EXCEPTION;

public enum CustomerStatus {
    NORMAL("normal"),
    BLACK("black");

    private final String type;

    CustomerStatus(String type) {
        this.type = type;
    }

    public static CustomerStatus of(String customerType) {
        return Arrays.stream(CustomerStatus.values())
                .filter(type -> type.type.equals(customerType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(CUSTOMER_STATUS_FIND_EXCEPTION.getMessage()));
    }
}
