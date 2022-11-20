package org.prgrms.vouchermanagement.io;

import org.prgrms.vouchermanagement.exception.command.InCorrectCommandException;

import java.util.Arrays;

public enum Command {
    NONE("none"),
    EXIT("exit"),
    CREATE("create"),
    LIST("list"),
    CUSTOMER_VOUCHER_LIST("customer vouchers list"),
    CREATE_CUSTOMER("create customer"),
    CUSTOMER_LIST("show customers"),
    DELETE_CUSTOMER_VOUCHER("delete"),
    BLACKLIST("blacklist");

    private final String userInputCommand;

    Command(String userInputCommand) {
        this.userInputCommand = userInputCommand;
    }

    public static Command findCommand(String input) {
        return Arrays.stream(values())
                .filter(command -> command.isMatchCommand(input))
                .findAny()
                .orElseThrow(InCorrectCommandException::new);
    }

    private boolean isMatchCommand(String input) {
        return userInputCommand.equals(input);
    }

    public boolean isNotExit() {
        return this != EXIT;
    }
}
