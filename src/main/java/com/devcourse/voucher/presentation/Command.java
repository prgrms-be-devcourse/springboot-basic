package com.devcourse.voucher.presentation;

import java.util.Arrays;

public enum Command {
    CREATE,
    LIST,
    EXIT;

    private static final String NOT_SUPPORT_COMMAND = "[Error] Your Input Is Not Support Command : ";

    public static Command from(String input) {
        return Arrays.stream(Command.values())
                .filter(command -> isCommand(command.name(), input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(NOT_SUPPORT_COMMAND + input));
    }

    public boolean isCreation() {
        return this == CREATE;
    }

    private static boolean isCommand(String command, String input) {
        return command.equals(input.toUpperCase());
    }
}
