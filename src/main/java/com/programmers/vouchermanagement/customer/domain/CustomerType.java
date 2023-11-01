package com.programmers.vouchermanagement.customer.domain;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum CustomerType {
    NORMAL,
    BLACK;

    public static final String INCORRECT_TYPE_MESSAGE = "%s is incorrect type of customer!";

    public static CustomerType findCustomerType(String input) {
        return Arrays.stream(CustomerType.values())
                .filter(type -> type.isMatching(input))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(INCORRECT_TYPE_MESSAGE.formatted(input)));
    }

    private boolean isMatching(String input) {
        return input.equalsIgnoreCase(this.name());
    }
}
