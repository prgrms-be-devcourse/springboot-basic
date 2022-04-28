package org.programmers.kdt.weekly.command;

import java.util.Arrays;

public enum VoucherCommandType {
    DEFAULT("default", "init"),
    VOUCHER_CREATE("create", "Type create to create a new voucher."),
    VOUCHER_LIST("list", "Type list to list all vouchers."),
    EXIT("exit", "Type exit to exit the program.");

    private final String command;
    private final String description;

    VoucherCommandType(String command, String description) {
        this.command = command;
        this.description = description;
    }

    public String getCommandMessage() {
        return description;
    }

    public static VoucherCommandType of(String userInput) {
        return Arrays.stream(VoucherCommandType.values())
            .filter(c -> c.command.equals(userInput))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }

    public boolean isRunnable() {
        return this != VoucherCommandType.EXIT;
    }
}
