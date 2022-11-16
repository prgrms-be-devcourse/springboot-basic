package org.prgrms.java.common;

import org.prgrms.java.exception.CommandException;

import java.util.Arrays;

public enum Command {
    EXIT,
    CREATE,
    LIST,
    BLACKLIST;

    public static Command get(String command) {
        return Arrays.stream(Command.values())
                .filter((item) -> item.toString().equals(command.toUpperCase()))
                .findAny()
                .orElseThrow(() -> new CommandException("Invalid input."));
    }
}
