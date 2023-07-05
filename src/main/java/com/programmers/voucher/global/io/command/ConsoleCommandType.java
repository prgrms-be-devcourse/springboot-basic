package com.programmers.voucher.global.io.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Objects;

import static com.programmers.voucher.global.util.ConsoleErrorMessages.INVALID_CONSOLE_COMMAND;

public enum ConsoleCommandType implements CommandType {
    HELP("help"),
    EXIT("exit"),
    CUSTOMER("customer"),
    VOUCHER("voucher");

    private static final Logger LOG = LoggerFactory.getLogger(ConsoleCommandType.class);

    private final String type;

    ConsoleCommandType(String type) {
        this.type = type;
    }

    public static ConsoleCommandType getValue(String type) {
        return Arrays.stream(values())
                .filter(i -> Objects.equals(i.type, type))
                .findAny()
                .orElseThrow(() -> {
                    String errorMessage = MessageFormat.format(INVALID_CONSOLE_COMMAND, type);

                    LOG.warn(errorMessage);
                    return new IllegalArgumentException(errorMessage);
                });
    }

    @Override
    public String getType() {
        return type;
    }
}
