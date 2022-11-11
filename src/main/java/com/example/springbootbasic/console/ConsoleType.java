package com.example.springbootbasic.console;

import java.util.Arrays;
import java.util.Optional;

public enum ConsoleType {

    VOUCHER_MENU("voucher-menu"),
    VOUCHER_CREATE("create"),
    VOUCHER_LIST("list"),
    VOUCHER_EXIT("exit"),
    CUSTOMER_MENU("customer-menu"),
    CUSTOMER_BLACK_LIST("customer-black-list");

    private final String type;

    ConsoleType(String type) {
        this.type = type;
    }

    public static Optional<ConsoleType> findConsoleTypeBy(String findType) {
        return Arrays.stream(ConsoleType.values())
                .filter(type -> type.type.equals(findType))
                .findFirst();
    }
}
