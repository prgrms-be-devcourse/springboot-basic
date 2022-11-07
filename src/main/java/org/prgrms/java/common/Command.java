package org.prgrms.java.common;

import org.prgrms.java.exception.CommandException;

import java.util.Arrays;

public enum Command {
    EXIT("exit"),
    CREATE("create"),
    LIST("list");

    private final String command;

    Command(String command) {
        this.command = command;
    }

    public static Command get(String command) {
        return Arrays.stream(Command.values())
                .filter((item) -> item.command.equals(command.toLowerCase()))
                .findAny()
                .orElseThrow(() -> new CommandException("Invalid input."));
    }
}
