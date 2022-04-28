package org.devcourse.voucher.model;

public enum Command {
    EXIT("exit"),
    CREATE("create"),
    LIST("list"),
    NONE("");

    private final String command;

    Command(String command) {
        this.command = command;
    }

    public static Command discriminate(String input) {
        Command command;

        switch (input) {
            case "exit" -> command = EXIT;
            case "create" -> command = CREATE;
            case "list" -> command = LIST;
            default -> command = NONE;
        }
        return command;
    }
}
