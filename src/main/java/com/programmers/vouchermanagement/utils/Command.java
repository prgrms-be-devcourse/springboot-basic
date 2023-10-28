package com.programmers.vouchermanagement.utils;

import com.programmers.vouchermanagement.exception.CommandNotFoundException;

import java.util.Arrays;

public enum Command {

    CREATE_VOUCHER("1"),
    LIST_VOUCHER("2"),
    ONE_VOUCHER("3"),
    UPDATE_VOUCHER("4"),
    DELETE_VOUCHER("5"),
    BLACKLIST("6"),
    CREATE_WALLET("7"),
    LIST_WALLET_VOUCHER("8"),
    LIST_WALLET_CUSTOMER("9"),
    DELETE_WALLET("10"),
    EXIT("11");

    private final String commandNumber;

    Command(String commandNumber) {
        this.commandNumber = commandNumber;
    }

    public static Command getCommandByNumber(String commandNumber) {
        return Arrays.stream(Command.values())
                .filter(command -> command.getCommandNumber().equals(commandNumber))
                .findAny().orElseThrow(CommandNotFoundException::new);
    }

    public static Command getCommandByName(String name) {
        return Arrays.stream(Command.values())
                .filter(command -> command.getName().equalsIgnoreCase(name))
                .findAny().orElseThrow(CommandNotFoundException::new);
    }

    private String getName() {
        return this.name();
    }

    private String getCommandNumber() {
        return commandNumber;
    }
}
