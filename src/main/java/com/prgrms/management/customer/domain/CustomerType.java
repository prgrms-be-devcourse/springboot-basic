package com.prgrms.management.customer.domain;

import com.prgrms.management.command.domain.Command;
import com.prgrms.management.config.ErrorMessage;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum CustomerType {
    NORMAL,BLACKLIST;

    public static CustomerType of(String input) {
        return Arrays.stream(CustomerType.values())
                .filter(e -> e.name().equals(input.toUpperCase()))
                .findAny()
                .orElseThrow(()-> new NoSuchElementException(CustomerType.class+ ErrorMessage.NOT_CUSTOMER_TYPE.getMessage()));
    }
}
