package com.programmers.vouchermanagement.view;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Command {

    CREATE("create"),
    LIST("list"),
    EXIT("exit");

    private final String name;
    private static final Map<String, Command> COMMAND_MAP;

    static {
        COMMAND_MAP = Collections.unmodifiableMap(Stream.of(values())
                .collect(Collectors.toMap(Command::getName, Function.identity())));
    }

    Command(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Command from(String name) {
        if (COMMAND_MAP.containsKey(name)) {
            return COMMAND_MAP.get(name);
        }
        throw new IllegalArgumentException("This command does not exist.");
    }
}
