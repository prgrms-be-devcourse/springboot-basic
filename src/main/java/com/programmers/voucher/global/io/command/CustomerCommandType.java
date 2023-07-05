package com.programmers.voucher.global.io.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Objects;

import static com.programmers.voucher.global.util.ConsoleErrorMessages.INVALID_CUSTOMER_CONSOLE_COMMAND;

public enum CustomerCommandType implements CommandType {
    CREATE("create"),
    LIST("list"),
    UPDATE("update"),
    DELETE("delete"),
    BLACKLIST("blacklist"),
    HELP("help"),
    EXIT("exit");

    private static final Logger LOG = LoggerFactory.getLogger(CustomerCommandType.class);

    private final String type;

    CustomerCommandType(String type) {
        this.type = type;
    }

    public static CustomerCommandType getValue(String type) {
        return Arrays.stream(values())
                .filter(t -> Objects.equals(t.type, type))
                .findAny()
                .orElseThrow(() -> {
                    String errorMessage = MessageFormat.format(INVALID_CUSTOMER_CONSOLE_COMMAND, type);

                    LOG.warn(errorMessage);
                    return new IllegalArgumentException(errorMessage);
                });
    }

    @Override
    public String getType() {
        return type;
    }
}
