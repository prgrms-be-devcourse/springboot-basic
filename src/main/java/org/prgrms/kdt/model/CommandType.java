package org.prgrms.kdt.model;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum CommandType {
    CREATE("create"),
    LIST("list"),
    EXIT("exit"),
    UNKNOWN("");

    private final String command;
    private static final Map<String, CommandType> commandMap = Collections.unmodifiableMap(
            Stream.of(values())
                    .collect(Collectors.toMap(CommandType::getCommand, Function.identity()))
    );

    public String getCommand() {
        return command;
    }

    CommandType(String command) {
        this.command = command;
    }

    public static CommandType findCommandType(String command) {
        return Optional.ofNullable(commandMap.get(command))
                .orElse(UNKNOWN);
    }
}
