package com.example.springbootbasic.io;

import java.util.Arrays;
import java.util.Optional;

public enum Command {
    EXIT("exit"), CREATE("create"), LIST("list");
    private final String commandValue;

    public static Optional<Command> valueOfCommand(String commandValue) {
        return Arrays.stream(values())
                .filter(value -> value.commandValue.equals(commandValue))
                .findAny();
    }

    Command(String commandValue) {
        this.commandValue = commandValue;
    }

    public String getCommandValue() {
        return commandValue;
    }
}
