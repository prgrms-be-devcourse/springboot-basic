package com.programmers.springweekly.domain.customer;

import java.util.Arrays;

public enum CustomerType {
    NORMAL("normal"),
    BLACKLIST("blacklist");

    private final String type;

    CustomerType(String type) {
        this.type = type;
    }

    public static CustomerType findCustomerType(String type){
        return Arrays.stream(CustomerType.values())
                .filter((item) -> item.type.equals(type))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("The type you are looking for is not found."));
    }
}
