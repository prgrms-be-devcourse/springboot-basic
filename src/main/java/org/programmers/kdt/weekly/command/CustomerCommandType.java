package org.programmers.kdt.weekly.command;

import java.util.Arrays;

public enum CustomerCommandType {
    DEFAULT("default", "init"),
    CUSTOMER_CREATE("create", "Type create to create a new customer ."),
    CUSTOMER_LIST("list", "Type list to list all customers."),
    CUSTOMER_BLACK_LIST("blacklist", "Type blacklist to list all customers."),
    CUSTOMER_TYPE_CHANGE("change", "Type type to change customer Type."),
    EXIT("exit", "Type exit to exit the program.");

    private final String command;
    private final String description;

    CustomerCommandType(String command, String description) {
        this.command = command;
        this.description = description;
    }

    public String getCommandMessage() {
        return description;
    }

    public static CustomerCommandType findByCommand(String userInput) {
        return Arrays.stream(CustomerCommandType.values())
            .filter(c -> c.command.equals(userInput))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }

    public boolean isRunnable() {
        return this != CustomerCommandType.EXIT;
    }
}
