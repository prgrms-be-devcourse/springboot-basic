package com.prgrms.management.customer.domain;

import com.prgrms.management.command.domain.Command;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum CustomerType {
    NORMAL,BLACKLIST;

    public static CustomerType of(String input) {
        return Arrays.stream(CustomerType.values())
                .filter(e -> e.name().equals(input.toUpperCase()))
                .findAny()
                .orElseThrow(()-> new NoSuchElementException(CustomerType.class+":존재하지 않는 손님 타입입니다."));
    }
}
