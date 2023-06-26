package com.programmers.voucher.enumtype;

import com.programmers.voucher.util.ConsoleErrorMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;

public enum ConsoleCommandType {
    CREATE("create"),
    LIST("list"),
    HELP("help"),
    EXIT("exit");

    private static final Logger LOG = LoggerFactory.getLogger(ConsoleCommandType.class);

    private final String input;

    ConsoleCommandType(String input) {
        this.input = input;
    }

    public static ConsoleCommandType getValue(String input) {
        return Arrays.stream(values())
                .filter(i -> Objects.equals(i.input, input))
                .findAny()
                .orElseThrow(() -> {
                    String errorMessage = ConsoleErrorMessages.INVALID_CONSOLE_COMMAND + input;

                    LOG.warn("Invalid command type: {}", errorMessage);
                    return new IllegalArgumentException(errorMessage);
                });
    }

    public String getInput() {
        return input;
    }
}
