package com.programmers.voucher.global.io.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private static final Map<String, ConsoleCommandType> types =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(ConsoleCommandType::getType, Function.identity())));

    public static ConsoleCommandType getValue(String type) {
        return Optional.ofNullable(types.get(type))
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
