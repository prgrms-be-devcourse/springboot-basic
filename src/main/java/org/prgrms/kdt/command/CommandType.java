package org.prgrms.kdt.command;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum CommandType {
    CREATE("create"),
    LIST("list"),
    BLACK("black"),
    EXIT("exit");

    private final String command;
    private static final Map<String, CommandType> COMMAND_MAP = Collections.unmodifiableMap(
            Stream.of(values())
                    .collect(Collectors.toMap(CommandType::getCommand, Function.identity()))
    );

    public String getCommand() {
        return command;
    }

    CommandType(String command) {
        this.command = command;
    }

    public static CommandType of(String command) {
        Objects.requireNonNull(command, "Command should be not null.");

        return Optional.ofNullable(COMMAND_MAP.get(command.toLowerCase()))
                .orElseThrow(() -> new IllegalArgumentException("Please enter among " + names() + "." + System.lineSeparator()));
    }

    private static String names() {
        return Arrays.stream(CommandType.values())
                .map(CommandType::getCommand)
                .collect(Collectors.joining(", "));
    }
}
