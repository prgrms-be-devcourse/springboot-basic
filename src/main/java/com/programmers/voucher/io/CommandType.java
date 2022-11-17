package com.programmers.voucher.io;

import java.util.stream.Stream;

public enum CommandType {
    EXIT("1"),
    CREATE_VOUCHER("2"),
    LIST_VOUCHER("3"),
    SELECT_VOUCHER("4"),
    DELETE_ALL_VOUCHER("5"),
    CREATE_CUSTOMER("6"),
    BLACKS_CUSTOMER("7");

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
