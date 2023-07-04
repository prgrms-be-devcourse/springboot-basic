package com.programmers.domain.customer;

import java.util.Arrays;
import java.util.Objects;

public enum CustomerType {
    NORMAL("normal"),
    BLACK("black");

    private final String name;

    CustomerType(String name) {
        this.name = name;
    }

    public static CustomerType findCustomerType(String input) {
        return Arrays.stream(CustomerType.values())
                .filter(CustomerType -> Objects.equals(CustomerType.name, input))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public String toString() {
        return name;
    }
}
