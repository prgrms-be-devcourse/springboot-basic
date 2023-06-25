package com.programmers.console.util;

import com.programmers.global.exception.MenuTypeFormatException;

import java.util.Arrays;

public enum CommandType {
    LIST("list"),
    CREATE("create"),
    EXIT("exit");

    private final String command;

    CommandType(String command) {
        this.command = command;
    }

    public static CommandType findByCommand(String command) {
        return Arrays.stream(CommandType.values())
                .filter(menuType -> menuType.command.equals(command))
                .findFirst()
                .orElseThrow(MenuTypeFormatException::new);
    }
}
