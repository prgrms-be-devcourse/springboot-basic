package org.prgrms.java.common.command;

import org.prgrms.java.exception.notfound.CommandNotFoundException;

import java.util.Arrays;

public enum MenuCommand {
    EXIT, VOUCHER, CUSTOMER, WALLET;

    public static MenuCommand get(String command) {
        return Arrays.stream(MenuCommand.values())
                .filter((item) -> item.toString().equals(command.toUpperCase()))
                .findAny()
                .orElseThrow(CommandNotFoundException::new);
    }
}
