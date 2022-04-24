package com.prgrms.vouchermanagement.command;

import java.util.Arrays;

public enum MainCommand {
    CREATE_VOUCHER_COMMAND("voucher"), CREATE_CUSTOMER_COMMAND("customer"), LIST_COMMAND("list"), EXIT_COMMAND("exit"),
    BLACK_LIST_COMMAND("blacklist"), WALLET("wallet");

    private final String commandStr;

    MainCommand(String commandStr) {
        this.commandStr = commandStr;
    }

    public static MainCommand getCommand(String commandStr) throws IllegalArgumentException {
        return Arrays.stream(MainCommand.values())
                .filter(command -> command.commandStr.equals(commandStr))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("commandStr과 매치되는 COMMAND가 없습니다."));
    }

    public static boolean contain(String commandStr) {
        return Arrays.stream(MainCommand.values())
                .anyMatch(command -> command.commandStr.equals(commandStr));
    }
}
