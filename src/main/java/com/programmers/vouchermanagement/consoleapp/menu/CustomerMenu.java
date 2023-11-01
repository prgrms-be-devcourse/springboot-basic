package com.programmers.vouchermanagement.consoleapp.menu;

import java.util.Arrays;
import java.util.Objects;

public enum CustomerMenu {
    CREATE("1"),
    LIST("2"),
    SEARCH("3"),
    UPDATE("4"),
    DELETE("5"),
    BLACKLIST("6"),
    SEARCH_VOUCHERS("7"),
    REMOVE_VOUCHER("8"),
    INCORRECT_MENU("Incorrect Menu");

    private final String menuCode;

    CustomerMenu(String menuCode) {
        this.menuCode = menuCode;
    }

    public static CustomerMenu findCustomerMenu(String input) {
        return Arrays.stream(CustomerMenu.values())
                .filter(menu -> menu.isMatching(input))
                .findFirst()
                .orElse(INCORRECT_MENU);
    }

    private boolean isMatching(String input) {
        return Objects.equals(menuCode, input);
    }
}
