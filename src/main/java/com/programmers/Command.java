package com.programmers;

import java.util.Arrays;

public enum Command {
    CREATE("create"),
    LIST("list"),
    EXIT("exit");

    private final String stringCommand;

    Command(String command) {
        this.stringCommand = command;
    }

    public static Command getCommand(String inputCommand) {
        return Arrays.stream(Command.values())
                .filter(command -> command.stringCommand.equals(inputCommand))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("잘못된 명령입니다."));
    }

}
