package org.programmers.applicationcontext;

import java.util.Arrays;

public enum CommandType {

    CREATE("create"),
    LIST("list"),
    EXIT("exit");

    private String command;
    CommandType(String command) {
        this.command = command;
    }

    public static CommandType of(String str) {
        return Arrays.stream(CommandType.values())
                .filter(commandType -> str.equals(commandType.command))
                .findFirst()
                .orElse(null);
    }

}
