package com.prgms.VoucherApp.view;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Command {
    EXIT("exit"), CREATE("create"), LIST("list");

    private final String command;
    private static final Map<String, Command> COMMAND_MAP = Collections.unmodifiableMap(Arrays.stream(values())
            .collect(Collectors.toMap(Command::getCommand, Function.identity())));

    Command(String command) {
        this.command = command;
    }

    private String getCommand() {
        return this.command;
    }

    public static Command findByCommand(String command) {
        return COMMAND_MAP.get(command);
    }
}
