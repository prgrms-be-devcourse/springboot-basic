package com.programmers.voucher.enumtype;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;

public enum ConsoleCommandType {
    CREATE("create"),
    LIST("list"),
    HELP("help"),
    EXIT("exit");

    private static final Logger log = LoggerFactory.getLogger(ConsoleCommandType.class);

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

                    log.warn("Invalid command type: {}", sb);
                    return new IllegalArgumentException(sb.toString());
                });
    }
}
