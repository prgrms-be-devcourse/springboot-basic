package com.programmers.vouchermanagement.consoleapp.menu;

import java.util.Arrays;
import java.util.Objects;

public enum VoucherMenu {
    CREATE("1"),
    LIST("2"),
    SEARCH("3"),
    UPDATE("4"),
    DELETE("5"),
    INCORRECT_MENU("Incorrect Menu");

    private final String menuCode;

    VoucherMenu(String menuCode) {
        this.menuCode = menuCode;
    }

    public static VoucherMenu findVoucherMenu(String input) {
        return Arrays.stream(VoucherMenu.values())
                .filter(menu -> menu.isMatching(input))
                .findFirst()
                .orElse(INCORRECT_MENU);
    }

    private boolean isMatching(String input) {
        return Objects.equals(menuCode, input);
    }
}
