package com.programmers;

import java.util.Arrays;
import java.util.Optional;

public enum Command {
    CREATE("create"),
    LIST("list"),
    EXIT("exit"),
    ERROR("error");

    private final String command;

    Command(String command) {
        this.command = command;
    }

    public static Command getCommand(String inputCommand) {
        Optional<Command> optional = Arrays.stream(Command.values())
                .filter(o -> o.command.equals(inputCommand))
                .findFirst();

        if (optional.isEmpty()) {
            return ERROR;
        }
        return optional.get();
    }

}
