package com.demo.voucher.io;

import java.util.Arrays;

public enum CommandType {
    EXIT("exit", "Type exit to exit the program."),
    CREATE("create", "Type create to create a new voucher."),
    LIST("list", "Type list to list all vouchers.");

    private final String command;
    private final String description;


    CommandType(String command, String description) {
        this.command = command;
        this.description = description;
    }

    public static boolean isValidCommandInput(String input) {
        return Arrays.stream(CommandType.values())
                .filter(c -> c.getCommand().equals(input))
                .count() == 1;
    }

    public String getCommand() {
        return command;
    }

    @Override
    public String toString() {
        return description;
    }
}
