package com.prgrms.springbootbasic.app;

import java.text.MessageFormat;
import java.util.Arrays;

enum CommandType {
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

    public static CommandType findByCommand(String inputCommand) {
        return Arrays.stream(values())
                .filter(commandType -> commandType.getCommand().equals(inputCommand))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        MessageFormat.format("Input command {0} is invalid. Please select again.", inputCommand)));
    }
}
