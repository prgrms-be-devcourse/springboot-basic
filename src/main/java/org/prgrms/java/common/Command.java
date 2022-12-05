package org.prgrms.java.common;

import org.prgrms.java.exception.CommandException;

import java.util.Arrays;

public enum Command {
    EXIT,
    VOUCHER,
    CUSTOMER,
    WALLET,
    CREATE,
    FIND,
    LIST,
    BLACKLIST,
    ALLOCATE,
    SHOW,
    DELETE,
    DELETE_ALL;

    public static Command get(String command) {
        return Arrays.stream(Command.values())
                .filter((item) -> item.toString().equals(command.toUpperCase()))
                .findAny()
                .orElseThrow(() -> new CommandException("Please enter a valid command."));
    }
}
