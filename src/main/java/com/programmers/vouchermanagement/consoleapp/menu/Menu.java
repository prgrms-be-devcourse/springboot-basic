package com.programmers.vouchermanagement.consoleapp.menu;

import java.util.Arrays;
import java.util.Objects;

public enum Menu {
    EXIT("0"),
    VOUCHER("1"),
    CUSTOMER("2"),
    INCORRECT_MENU("Incorrect Menu");

    private final String menuCode;

    Menu(String menuCode) {
        this.menuCode = menuCode;
    }

    //set static to tell that this method does not depend on a particular Menu value
    public static Menu findMenu(String input) {
        return Arrays.stream(Menu.values())
                .filter(menu -> menu.isMatching(input))
                .findFirst()
                .orElse(INCORRECT_MENU);
    }

    private boolean isMatching(String input) {
        return Objects.equals(menuCode, input);
    }

    public boolean isExit() {
        return this == EXIT;
    }

    public boolean isIncorrect() {
        return this == INCORRECT_MENU;
    }
}
