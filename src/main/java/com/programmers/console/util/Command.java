package com.programmers.console.util;

import com.programmers.console.exception.MenuTypeFormatException;

import java.util.Arrays;

public enum Command {
    LIST("list"),
    CREATE("create"),
    EXIT("exit");

    private final String command;

    Command(String command) {
        this.command = command;
    }

    public static Command findByCommand(String command) {
        return Arrays.stream(Command.values())
                .filter(menuType -> menuType.command.equals(command))
                .findFirst()
                .orElseThrow(MenuTypeFormatException::new);
    }
}
