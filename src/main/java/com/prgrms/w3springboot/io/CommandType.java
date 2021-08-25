package com.prgrms.w3springboot.io;

import java.util.Arrays;

public enum CommandType {
    CREATE("create"),
    LIST("list"),
    EXIT("exit"),
    UNKNOWN("unknown");

    private String command;

    CommandType(String command) {
        this.command = command;
    }

    public static CommandType of(final String commandType) {
        return Arrays.stream(CommandType.values())
                .filter(c -> c.isCommandType(commandType))
                .findFirst()
                .orElse(UNKNOWN);
    }

    private boolean isCommandType(final String commandType) {
        return this.command.equals(commandType);
    }

}

