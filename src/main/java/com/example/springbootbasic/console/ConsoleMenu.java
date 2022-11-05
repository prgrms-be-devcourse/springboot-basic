package com.example.springbootbasic.console;

import java.util.Arrays;
import java.util.Optional;

public enum ConsoleMenu {
    EXIT("exit"),
    CREATE("create"),
    LIST("list");

    private final String menuType;

    ConsoleMenu(String menuType) {
        this.menuType = menuType;
    }

    public static Optional<ConsoleMenu> findMenu(String findMenuType) {
        return Arrays.stream(ConsoleMenu.values())
                .filter(menu -> menu.menuType.equals(findMenuType))
                .findFirst();
    }
}
