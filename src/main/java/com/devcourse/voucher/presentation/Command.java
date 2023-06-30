package com.devcourse.voucher.presentation;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public enum Command {
    CREATE("create"),
    LIST("list"),
    EXIT("exit");

    private static final String NOT_SUPPORT_COMMAND = "[Error] Your Input Is Not Support Command : ";

    private final String symbol;

    Command(String symbol) {
        this.symbol = symbol;
    }

    public static Command from(String input) {
        return Arrays.stream(Command.values())
                .filter(command -> isSame(command, input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(NOT_SUPPORT_COMMAND + input));
    }

    private static boolean isSame(Command command, String input) {
        return StringUtils.equals(command.symbol, input.toLowerCase());
    }
}
