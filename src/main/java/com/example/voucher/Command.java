package com.example.voucher;

import java.util.Arrays;

public enum Command {
    CREATE("create"),
    LIST("list");

    private final String command;

    Command(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public static Command fromString(String command) {
        return Arrays.stream(values())
                .filter(value -> value.getCommand().equalsIgnoreCase(command))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid command: " + command));
    }
}
