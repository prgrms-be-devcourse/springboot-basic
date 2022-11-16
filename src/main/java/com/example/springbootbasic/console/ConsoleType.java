package com.example.springbootbasic.console;

import java.text.MessageFormat;
import java.util.Arrays;

import static com.example.springbootbasic.exception.ConsoleTypeExceptionMessage.CONSOLE_TYPE_FIND_EXCEPTION;

public enum ConsoleType {

    VOUCHER_CREATE("create"),
    VOUCHER_LIST("list"),
    CUSTOMER_BLACK_LIST("customer-black-list"),
    EXIT("exit"),
    CONTINUE("continue");

    private final String type;

    ConsoleType(String type) {
        this.type = type;
    }

    public static ConsoleType of(String findType) {
        return Arrays.stream(ConsoleType.values())
                .filter(type -> type.type.equals(findType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format(CONSOLE_TYPE_FIND_EXCEPTION.getMessage(), findType)));
    }
}
