package com.prgms.springbootbasic.util;

import java.util.Arrays;
import java.util.List;

public enum CustomerMenu {

    BLACKLIST("list");

    private static final List<CustomerMenu> CUSTOMER_MENU = Arrays.stream(CustomerMenu.values()).toList();
    private final String command;

    CustomerMenu(String command) {
        this.command = command;
    }

    public static CustomerMenu of(String command) {
        return CUSTOMER_MENU.stream()
                .filter(c -> c.command.equals(command))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.NO_SUCH_MENU.getMessage()));
    }

}
