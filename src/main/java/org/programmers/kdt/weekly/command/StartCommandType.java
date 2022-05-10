package org.programmers.kdt.weekly.command;

import java.util.Arrays;

public enum StartCommandType {
    DEFAULT("default", "=== Management Program ==="),
    VOUCHER("voucher", "Type voucher to voucher menu"),
    CUSTOMER("customer", "Type customer to customer menu"),
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

    public static StartCommandType of(String userInput) {
        return Arrays.stream(StartCommandType.values())
            .filter(c -> c.command.equals(userInput))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }

    public boolean isRunnable() {
        return this != StartCommandType.EXIT;
    }
}
