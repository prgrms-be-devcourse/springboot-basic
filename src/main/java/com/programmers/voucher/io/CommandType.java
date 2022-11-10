package com.programmers.voucher.io;

import java.util.stream.Stream;

public enum CommandType {
    EXIT("exit"),
    CREATE("create"),
    LIST("list");

    private final String commandType;

    CommandType(String menuType) {
        this.commandType = menuType;
    }

    public Object getCommandType() {
        return commandType;
    }

    public static CommandType toMenuType(String inputMenu) {
        return Stream.of(CommandType.values())
                .filter(type -> type.getCommandType().equals(inputMenu))
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException(Message.WRONG_ORDER_MESSAGE.toString()));
    }
}
