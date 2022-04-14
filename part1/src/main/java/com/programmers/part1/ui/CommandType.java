package com.programmers.part1.ui;

public enum CommandType {

    EXIT,
    NONE,
    CREATE,
    LIST,
    BLACKLIST;

    public static CommandType getCommandType(String command) {
        return switch (command) {
            case "exit" -> EXIT;
            case "create" -> CREATE;
            case "list" -> LIST;
            case "blacklist" -> BLACKLIST;
            default -> NONE;
        };
    }
}
