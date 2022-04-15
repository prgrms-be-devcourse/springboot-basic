package org.prgrms.kdt;

import java.util.stream.Stream;

public enum CommandType {
    EXIT("exit"),
    CREATE("create"),
    LIST("list"),
    BLACK_LIST("black-list"),
    INVALID("invalid");

    private final String commandType;

    CommandType(String commandType) {
        this.commandType = commandType;
    }

    public String getCommandType() {
        return commandType;
    }

    public static CommandType getCommandType(String commandType) {
        return Stream.of(CommandType.values())
                .filter(type -> type.getCommandType().equalsIgnoreCase(commandType))
                .findFirst()
                .orElse(CommandType.INVALID);
    }
}
