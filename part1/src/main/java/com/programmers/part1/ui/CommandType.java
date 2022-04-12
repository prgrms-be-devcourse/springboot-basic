package com.programmers.part1.ui;

public enum CommandType {

    EXIT,
    NONE,
    CREATE,
    LIST,
    BLACKLIST;

    public static CommandType getCommandType(String input) {
        return switch (input) {
            case "exit" -> EXIT;
            case "create" -> CREATE;
            case "list" -> LIST;
            case "blacklist" -> BLACKLIST;
            default -> NONE;
        };
    }
}
