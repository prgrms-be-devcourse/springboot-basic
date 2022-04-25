package org.prgrms.voucher;

import java.util.Arrays;

public enum CommandType {

    CREATE("create", "Type create to create a new Voucher."),
    LIST("list", "Type list to list all vouchers."),
    EXIT("exit", "Type exit to exit the program.");

    private final String command;
    private final String prompt;

    public String getPrompt() {

        return prompt;
    }

    CommandType(String command, String prompt) {

        this.command = command;
        this.prompt = prompt;
    }

    public static CommandType findByCommand(String userInput) {

        return Arrays.stream(CommandType.values())
                .filter(v -> v.command.equals(userInput))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

