package com.programmers.voucher.uitl;

import java.util.Arrays;

public enum MenuType {
    LIST("list"),
    CREATE("create"),
    EXIT("exit");

    private final String command;

    MenuType(String command) {
        this.command = command;
    }

    public static MenuType getCommand(String command) {
        return Arrays.stream(MenuType.values())
                .filter(menuType -> menuType.command.equals(command))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}
