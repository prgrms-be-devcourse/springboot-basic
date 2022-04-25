package com.waterfogsw.voucher.console;

import java.io.IOException;
import java.util.stream.Stream;

import static com.waterfogsw.voucher.console.Messages.INVALID_COMMAND;

public enum Command {
    CREATE("create"),
    LIST("list"),
    BLACK_LIST("BLACK_LIST"),
    EXIT("exit");

    private final String command;

    Command(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public static Command getCommand(String command) throws IOException {
        return Stream.of(Command.values())
                .filter(i -> i.getCommand().equals(command.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new IOException(INVALID_COMMAND));
    }
}
