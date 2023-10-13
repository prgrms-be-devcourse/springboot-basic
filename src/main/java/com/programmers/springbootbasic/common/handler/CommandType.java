package com.programmers.springbootbasic.common.handler;

import java.util.Arrays;

public enum CommandType {
    Init(""),
    Voucher("voucher"),
    Customer("customer"),
    Create("create"),
    List("list"),
    Blacklist("blacklist"),
    Exit("exit"),
    Error("error");

    private final String command;

    CommandType(String command) {
        this.command = command;
    }

    public static CommandType from(String command) {
        return Arrays.stream(CommandType.values())
                .filter(commandType -> commandType.command.equals(command))
                .findAny()
                .orElse(Error);
    }


}
