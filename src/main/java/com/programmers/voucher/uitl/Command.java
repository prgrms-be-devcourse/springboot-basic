package com.programmers.voucher.uitl;

import com.programmers.voucher.exception.MenuTypeFormatException;

import java.util.Arrays;

public enum Command {
    LIST("list"),
    CREATE("create"),
    EXIT("exit");

    private final String command;

    Command(String command) {
        this.command = command;
    }

    public static Command getCommand(String command) {
        return Arrays.stream(Command.values())
                .filter(menuType -> menuType.command.equals(command))
                .findFirst()
                .orElseThrow(MenuTypeFormatException::new);
    }
}
