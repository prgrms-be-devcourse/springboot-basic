package com.prgrms.management.customer.domain;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static com.prgrms.management.config.ErrorMessageType.NOT_EXIST_TYPE;

public enum CustomerType {
    NORMAL, BLACKLIST;

    public static CustomerType of(String input) {
        return Arrays.stream(CustomerType.values())
                .filter(e -> e.name().equals(input.toUpperCase()))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException(CustomerType.class + NOT_EXIST_TYPE.getMessage()));
    }
}
