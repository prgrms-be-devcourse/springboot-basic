package com.programmers.springbootbasic.common.handler;

import java.util.Arrays;

public enum CommandType {
    init(""),
    voucher("voucher"),
    customer("customer"),
    create("create"),
    list("list"),
    find("find"),
    update("update"),
    delete("delete"),
    blacklist("blacklist"),
    addBlacklist("addBlacklist"),
    removeBlacklist("removeBlacklist"),
    deleteAll("deleteAll"),
    addVoucher("addVoucher"),
    removeVoucher("removeVoucher"),
    wallet("wallet"),
    exit("exit"),
    error("error");

    private final String command;

    CommandType(String command) {
        this.command = command;
    }

    public static CommandType from(String command) {
        return Arrays.stream(CommandType.values())
                .filter(commandType -> commandType.command.equals(command))
                .findAny()
                .orElse(error);
    }


}
