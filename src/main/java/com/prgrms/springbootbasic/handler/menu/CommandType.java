package com.prgrms.springbootbasic.handler.menu;

import java.util.Arrays;

public enum CommandType {
    CREATE("create"),
    LIST("list"),
    EXIT("exit");

    private final String command;

    CommandType(String command) {
        this.command = command;
    }

    private String getCommand() {
        return command;
    }

    public boolean isExit() {
        return this.equals(EXIT);
    }

    public static CommandType findByCommand(String inputCommand) {
        return Arrays.stream(values())
                .filter(commandType -> commandType.getCommand().equals(inputCommand))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid command. Please select again."));
    }
}
