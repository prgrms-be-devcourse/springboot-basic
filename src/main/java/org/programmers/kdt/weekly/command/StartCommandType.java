package org.programmers.kdt.weekly.command;

import java.util.Arrays;

public enum StartCommandType {
    DEFAULT("default", "init"),
    VOUCHER_CREATE("create", "Type create to create a new voucher."),
    CUSTOMER("customer", "Type customer to customer menu"),
    VOUCHER_LIST("list", "Type list to list all vouchers."),
    WALLET("wallet", "Type wallet to Voucher Wallet service"),
    EXIT("exit", "Type exit to exit the program.");

    private final String command;
    private final String description;

    StartCommandType(String command, String description) {
        this.command = command;
        this.description = description;
    }

    public String getCommandMessage() {
        return description;
    }

    public static StartCommandType findByCommand(String userInput) {
        return Arrays.stream(StartCommandType.values())
            .filter(c -> c.command.equals(userInput))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }

    public boolean isRunnable() {
        return this != StartCommandType.EXIT;
    }
}
