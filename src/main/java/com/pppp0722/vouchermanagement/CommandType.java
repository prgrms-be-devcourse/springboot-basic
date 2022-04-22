package com.pppp0722.vouchermanagement;

public enum CommandType {
    CREATE,
    LIST,
    BLACK,
    EXIT,
    NONE;

    public static CommandType getCommandType(String command) {
        String lowerCommand = command.toLowerCase();
        CommandType commandType;

        switch(lowerCommand) {
            case "create":
                commandType = CommandType.CREATE;
                break;
            case "list":
                commandType = CommandType.LIST;
                break;
            case "black":
                commandType = CommandType.BLACK;
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
