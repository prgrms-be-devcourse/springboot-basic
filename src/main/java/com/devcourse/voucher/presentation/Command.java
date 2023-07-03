package com.devcourse.voucher.presentation;

import java.util.Arrays;

public enum Command {
    CREATE,
    LIST,
    EXIT;

    private static final String NOT_SUPPORT_COMMAND = "[Error] Your Input Is Not Support Command : ";

    public static Command from(String input) {
        return Arrays.stream(Command.values())
                .filter(command -> isSameCommand(command.name(), input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(NOT_SUPPORT_COMMAND + input));
    }

    public boolean isCreation() {
        return this == CREATE;
    }

    private static boolean isSameCommand(String command, String input) {
        return command.equals(input.toLowerCase());
    }
}
