package com.programmers.voucher.io;

import java.util.stream.Stream;

public enum CommandType {
    EXIT("1"),
    CREATE("2"),
    LIST("3"),
    DELETE("4"),
    BLACKS("5");

    private final String commandType;

    CommandType(String commandType) {
        this.commandType = commandType;
    }

    public Object getCommandType() {
        return commandType;
    }

    public static CommandType toCommandType(String command) {
        return Stream.of(CommandType.values())
                .filter(type -> type.getCommandType().equals(command))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(Message.WRONG_ORDER_MESSAGE.toString()));
    }
}
