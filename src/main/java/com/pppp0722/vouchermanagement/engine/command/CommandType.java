package com.pppp0722.vouchermanagement.engine.command;

public enum CommandType {
    CREATE,
    READ,
    UPDATE,
    DELETE,
    EXIT,
    NONE;

    public static CommandType getCommandType(String input) {
        switch (input) {
            case "create":
                return CommandType.CREATE;
            case "read":
                return CommandType.READ;
            case "update":
                return CommandType.UPDATE;
            case "delete":
                return CommandType.DELETE;
            case "exit":
                return CommandType.EXIT;
            default:
                return CommandType.NONE;
        }
    }
}
