package org.programmers.customer.model;

import java.util.Arrays;

public enum CustomerInputType {
    EXIT("exit"),
    CREATE("create"),
    CUSTOMERLIST("customerlist"),
    FIND("find"),
    UPDATE("update"),
    DELETE("delete"),
    BLACKLIST("blacklist");

    private String input;

    CustomerInputType(String input) {
        this.input = input;
    }

    public static CustomerInputType getInputType(String input) {
        return Arrays.stream(CustomerInputType.values())
                .filter(voucherInputType -> voucherInputType.input.equals(input))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Wrong CustomerMode Input"));
    }
}
