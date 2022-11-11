package org.prgms.springbootbasic.cli;

import java.util.Arrays;

public enum Command {
    EXIT("exit"),
    CREATE("create"),
    LIST("list"),
    BLACKLIST("blacklist");

    private final String command;

    Command(String command) {
        this.command = command;
    }

    public static Command findCommand(String input) {
        return Arrays.stream(Command.values())
                .filter(e -> e.command.equals(input.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("wrong command entered :\t {" + input + "}"));
    }
}
