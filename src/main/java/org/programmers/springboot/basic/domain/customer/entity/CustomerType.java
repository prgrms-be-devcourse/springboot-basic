package org.programmers.springboot.basic.domain.customer.entity;

import org.programmers.springboot.basic.domain.customer.exception.CustomerNotFoundException;

import java.util.Arrays;

public enum CustomerType {

    NORMAL(0),
    BLACK(1);

    private final int value;

    CustomerType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static CustomerType valueOf(int value) {

        return Arrays.stream(values())
                .filter(customer -> customer.getValue() == value)
                .findAny()
                .orElseThrow(CustomerNotFoundException::new);
    }
}
