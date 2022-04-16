package com.example.voucher_manager.io;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum CommandType {
    CREATE("create"),
    LIST("list"),
    EXIT("exit"),
    BLACKLIST("blacklist"),
    ERROR("error");

    private final String command;

    private static final Map<String, CommandType> INPUT_COMMAND = Arrays.stream(CommandType.values())
                    .collect(Collectors.toMap(CommandType::getCommand, Function.identity()));

    CommandType(String command) {
        this.command = command;
    }

    public static CommandType of(String name){
        return INPUT_COMMAND.get(name);
    }

    public static boolean isValidated(String name) {
        return INPUT_COMMAND.containsKey(name);
    }

    private String getCommand() {
        return command;
    }
}
