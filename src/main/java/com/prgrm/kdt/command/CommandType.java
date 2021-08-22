package com.prgrm.kdt.command;

import java.util.Arrays;

public enum CommandType {

    UNDEFINED("undefined"),
    EXIT("exit"),
    CREATE("create"),
    LIST("list");

    private final String command;

    CommandType(final String command) {
        this.command = command;
    }

    public static CommandType getCommandType(String command){
        return Arrays.stream(CommandType.values())
                .filter(c -> c.command.equals(command))
                .findAny()
                .orElseThrow(RuntimeException::new);

    }
}
