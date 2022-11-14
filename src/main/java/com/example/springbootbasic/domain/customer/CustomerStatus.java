package com.example.springbootbasic.domain.customer;

import java.util.Arrays;
import java.util.Optional;

public enum CustomerStatus {
    NORMAL("normal"),
    BLACK("black");

    private final String type;

    CustomerStatus(String type) {
        this.type = type;
    }

    public static Optional<CustomerStatus> findCustomerBy(String customerType) {
        return Arrays.stream(CustomerStatus.values())
                .filter(type -> type.type.equals(customerType))
                .findFirst();
    }
}
