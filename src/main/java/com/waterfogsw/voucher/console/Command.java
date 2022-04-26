package com.waterfogsw.voucher.console;

import java.util.stream.Stream;

import static com.waterfogsw.voucher.console.Messages.INVALID_COMMAND;

public enum Command {
    CREATE("create"),
    LIST("list"),
    BLACKLIST("blacklist"),
    EXIT("exit");

    private final String command;

    Command(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public static Command getCommand(String command) {
        return Stream.of(Command.values())
                .filter(i -> i.getCommand().equals(command.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_COMMAND));
    }
}
