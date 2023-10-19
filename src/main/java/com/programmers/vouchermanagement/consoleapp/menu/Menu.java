package com.programmers.vouchermanagement.consoleapp.menu;

import java.util.Arrays;
import java.util.Objects;

public enum Menu {
    EXIT("exit"),
    CREATE("create"),
    LIST("list"),
    INCORRECT_MENU("incorrect menu");

    private final String menuName;

    Menu(String menuName) {
        this.menuName = menuName;
    }

    private boolean isMatching(String input) {
        return Objects.equals(menuName, input);
    }

    //set static to tell that this method does not depend on a particular Menu value
    public static Menu findMenu(String input) {
        return Arrays.stream(Menu.values())
                .filter(menu -> menu.isMatching(input))
                .findFirst()
                .orElse(INCORRECT_MENU);
    }

    public boolean isExit() {
        return this == Menu.EXIT;
    }

    public boolean isIncorrect() {
        return this == INCORRECT_MENU;
    }
}
