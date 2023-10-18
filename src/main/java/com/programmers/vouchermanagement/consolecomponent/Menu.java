package com.programmers.vouchermanagement.consolecomponent;

import java.util.Arrays;
import java.util.Objects;

public enum Menu {
    EXIT("exit", false),
    CREATE("create", true),
    LIST("list", true),
    INCORRECT_MENU("incorrect menu", false)
    ;

    private final String menuName;
    private final boolean executable;

    Menu(String menuName, boolean executable) {
        this.menuName = menuName;
        this.executable = executable;
    }

    private boolean isMatching(String input) {
        return Objects.equals(menuName, input);
    }

    //set static to tell that this method does not depend on a particular Menu value
    public static Menu findSelectedMenu(String input) {
        return Arrays.stream(Menu.values())
                .filter(menu -> menu.isMatching(input))
                .findFirst()
                .orElse(INCORRECT_MENU);
    }

    public boolean isExecutable() {
        return executable;
    }
}
