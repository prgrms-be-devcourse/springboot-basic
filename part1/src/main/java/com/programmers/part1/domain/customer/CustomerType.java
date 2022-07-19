package com.programmers.part1.domain.customer;

import java.util.Arrays;

public enum CustomerType {
    Normal("normal"), Black("black");

    private final String customerType;

    CustomerType(String customerType) {
        this.customerType = customerType;
    }

    public static CustomerType getCustomerType(String requestType){
        return Arrays.stream(values())
                .filter(o -> o.customerType.equals(requestType))
                .findAny()
                .orElseThrow(()-> new IllegalArgumentException("dd"));
    }
}
