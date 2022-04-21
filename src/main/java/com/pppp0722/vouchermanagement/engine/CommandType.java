package com.pppp0722.vouchermanagement.engine;

public enum CommandType {
    CREATE,
    LIST,
    DELETE,
    EXIT,
    NONE;

    public static CommandType getCommandType(String command) {
        command = command.toLowerCase();
        CommandType commandType;

        switch (command) {
            case "create":
                commandType = CommandType.CREATE;
                break;
            case "list":
                commandType = CommandType.LIST;
                break;
            case "delete":
                commandType = CommandType.DELETE;
                break;
            case "exit":
                commandType = CommandType.EXIT;
                break;
            default:
                commandType = CommandType.NONE;
                break;
        }

        return commandType;
    }
}
