package com.pppp0722.vouchermanagement.engine.command;

public enum CommandType {
    CREATE,
    READ,
    UPDATE,
    DELETE,
    EXIT,
    NONE;

    public static CommandType getCommandType(String input) {
        CommandType type;
        switch (input) {
            case "create":
                type = CommandType.CREATE;
                break;
            case "read":
                type = CommandType.READ;
                break;
            case "update":
                type = CommandType.UPDATE;
                break;
            case "delete":
                type = CommandType.DELETE;
                break;
            case "exit":
                type = CommandType.EXIT;
                break;
            default:
                type = CommandType.NONE;
                break;
        }
        return type;
    }
}
