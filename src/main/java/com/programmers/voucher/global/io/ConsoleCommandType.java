package com.programmers.voucher.global.io;

import com.programmers.voucher.global.util.CommonErrorMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;

import static com.programmers.voucher.global.util.ConsoleErrorMessages.INVALID_CONSOLE_COMMAND;

public enum ConsoleCommandType {
    CREATE("create"),
    LIST("list"),
    HELP("help"),
    BLACKLIST("blacklist"),
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
                    String errorMessage = CommonErrorMessages.currentInput(INVALID_CONSOLE_COMMAND, input);

                    LOG.warn(errorMessage);
                    return new IllegalArgumentException(errorMessage);
                });
    }

    public String getInput() {
        return input;
    }
}
