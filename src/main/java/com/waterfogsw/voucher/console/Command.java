package com.waterfogsw.voucher.console;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.waterfogsw.voucher.console.Messages.INVALID_COMMAND;

public enum Command {
    CREATE("Type create to create a new voucher."),
    LIST("Type list to list all vouchers."),
    BLACKLIST("Type blacklist to list black list."),
    EXIT("Type exit to exit the program.");

    private final String description;

    Command(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static String getAllDescriptions() {
        return Stream.of(Command.values())
                .map(Command::getDescription)
                .collect(Collectors.joining("\n"));
    }


    public static Command from(String command) {
        try {
            return Command.valueOf(command.toUpperCase());
        } catch (NullPointerException e) {
            throw new IllegalArgumentException(INVALID_COMMAND);
        }
    }
}
