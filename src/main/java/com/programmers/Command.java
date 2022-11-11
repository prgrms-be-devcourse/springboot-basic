package com.programmers;

import java.util.Arrays;

public enum Command {
    CREATE("create"),
    LIST("list"),
    EXIT("exit");

    private final String command;

    Command(String command) {
        this.command = command;
    }

    public static Command getCommand(String inputCommand) {
        return Arrays.stream(Command.values())
                .filter(o -> o.command.equals(inputCommand))
                .findFirst()
                .get();
    }

}
