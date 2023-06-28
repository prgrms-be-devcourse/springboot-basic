package com.demo.voucher.io;

import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum CommandType {
    EXIT("exit", "Type exit to exit the program."),
    CREATE("create", "Type create to create a new voucher."),
    LIST("list", "Type list to list all vouchers.");

    private static final Map<String, CommandType> COMMAND_MAP = Collections.unmodifiableMap(Stream.of(values())
            .collect(Collectors.toMap(CommandType::getCommand, Function.identity())));

    private final String command;
    private final String description;

    CommandType(String command, String description) {
        this.command = command;
        this.description = description;
    }

    public static boolean isValidCommandInput(String input) {
        return COMMAND_MAP.containsKey(input);
    }
}
