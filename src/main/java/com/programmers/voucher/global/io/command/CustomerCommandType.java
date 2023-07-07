package com.programmers.voucher.global.io.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private static final Map<String, CustomerCommandType> types =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(CustomerCommandType::getType, Function.identity())));

    public static CustomerCommandType getValue(String type) {
        return Optional.ofNullable(types.get(type))
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
