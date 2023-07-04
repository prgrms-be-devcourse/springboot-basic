package com.programmers.voucher.global.io.command;

import com.programmers.voucher.global.util.CommonErrorMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public static ConsoleCommandType getValue(String input) {
        return Arrays.stream(values())
                .filter(i -> Objects.equals(i.type, input))
                .findAny()
                .orElseThrow(() -> {
                    String errorMessage = CommonErrorMessages.addCurrentInput(INVALID_CONSOLE_COMMAND, input);

                    LOG.warn(errorMessage);
                    return new IllegalArgumentException(errorMessage);
                });
    }

    @Override
    public String getType() {
        return type;
    }
}
