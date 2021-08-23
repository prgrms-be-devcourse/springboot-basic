package com.prgrm.kdt.command;

import java.util.Arrays;

public enum CommandType {

    EXIT("exit"),
    CREATE("create"),
    LIST("list"),
    BLACK("black");

    private final String command;

    CommandType(final String command) {
        this.command = command;
    }

    public static CommandType getCommandType(String command){
        return Arrays.stream(CommandType.values())
                .filter(c -> c.command.equals(command))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Not Correct Message"));
    }
}
