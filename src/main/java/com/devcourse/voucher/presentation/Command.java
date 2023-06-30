package com.devcourse.voucher.presentation;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public enum Command {
    CREATE("create"),
    LIST("list"),
    EXIT("exit");

    private final String symbol;

    Command(String symbol) {
        this.symbol = symbol;
    }

    public static boolean isIncorrectCommand(String input) {
        return Arrays.stream(Command.values())
                .noneMatch(command -> isSame(command, input));
    }

    private static boolean isSame(Command command, String input) {
        return StringUtils.equals(command.symbol, input.toLowerCase());
    }
}
