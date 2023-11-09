package com.programmers.springbootbasic.common.handler;

import java.util.Arrays;

public enum CommandType {
    INIT(""),
    VOUCHER("voucher"),
    CUSTOMER("customer"),
    CREATE("create"),
    LIST("list"),
    FIND("find"),
    UPDATE("update"),
    DELETE("delete"),
    BLACKLIST("blacklist"),
    ADD_BLACKLIST("addBlacklist"),
    REMOVE_BLACKLIST("removeBlacklist"),
    DELETE_ALL("deleteAll"),
    ADD_VOUCHER("addVoucher"),
    REMOVE_VOUCHER("removeVoucher"),
    WALLET("wallet"),
    EXIT("exit"),
    ERROR("error");

    private final String command;

    CommandType(String command) {
        this.command = command;
    }

    public static CommandType from(String command) {
        return Arrays.stream(CommandType.values())
                .filter(commandType -> commandType.command.equals(command))
                .findAny()
                .orElse(ERROR);
    }


}
