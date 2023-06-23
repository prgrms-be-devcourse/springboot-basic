package com.programmers.voucher.enumtype;

import java.util.Arrays;
import java.util.Objects;

public enum ConsoleCommandType {
    CREATE("create"),
    LIST("list"),
    HELP("help"),
    EXIT("exit");

    private final String input;

    ConsoleCommandType(String input) {
        this.input = input;
    }

    public static ConsoleCommandType getValue(String input) {
        return Arrays.stream(values())
                .filter(i -> Objects.equals(i.input, input))
                .findAny()
                .orElseThrow(() -> {
                    StringBuilder sb = new StringBuilder("Command type is invalid.")
                            .append(" Current input: ")
                            .append(input);

                    return new IllegalArgumentException(sb.toString());
                });
    }
}
