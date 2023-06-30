package com.example.demo.util;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum CommandType {
    EXIT("exit"),
    CREATE("create"),
    LIST("list");

    private static final Map<String, CommandType> COMMAND_TYPE_MAP =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(CommandType::getCommand, Function.identity())));

    private final String command;

    CommandType(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public static CommandType find(String input) {
        if (COMMAND_TYPE_MAP.containsKey(input)) {
            return COMMAND_TYPE_MAP.get(input);
        }
        throw new IllegalArgumentException("잘 못된 명령어 입니다.");
    }
    
}
