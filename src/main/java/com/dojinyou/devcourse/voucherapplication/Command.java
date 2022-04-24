package com.dojinyou.devcourse.voucherapplication;

public enum Command {
    CREATE("to create a new voucher."),
    LIST("to list all vouchers."),
    EXIT("to exit the program");

    private static final String ERROR_MESSAGE_FOR_COMMAND_NOT_FOUND = "명령어를 찾을 수 없습니다.";
    private final String description;

    Command(String description) {
        this.description = description;
    }

    public static Command of(String commandString) {
        try {
            return Command.valueOf(commandString);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException(ERROR_MESSAGE_FOR_COMMAND_NOT_FOUND);
        }
    }

    public static String getCommandList() {
        StringBuilder sb = new StringBuilder();
        for (Command command:Command.values()) {
            sb.append("Type "+command.toString()+"to"+command.getDescription());
        }
        return sb.toString();
    }

    private String getDescription() {
        return this.description;
    }
}
