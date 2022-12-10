package org.prgrms.java.common;

import org.prgrms.java.exception.CommandException;

import java.util.Arrays;

public enum MenuCommand {
    EXIT, VOUCHER, CUSTOMER, WALLET;

    public static MenuCommand get(String command) {
        return Arrays.stream(MenuCommand.values())
                .filter((item) -> item.toString().equals(command.toUpperCase()))
                .findAny()
                .orElseThrow(() -> new CommandException("Please enter a valid menu command."));
    }
}
