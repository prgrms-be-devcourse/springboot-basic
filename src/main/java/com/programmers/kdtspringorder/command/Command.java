package com.programmers.kdtspringorder.command;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Command {
    EXIT("EXIT", "Type EXIT to exit the program"),
    CREATE("CREATE", "Type CREATE to create a new voucher"),
    VOUCHERS("VOUCHERS", "Type VOUCHERS to list all vouchers"),
    CUSTOMERS("CUSTOMERS", "Type CUSTOMERS to list all vouchers");

    private String command;
    private String message;
    private CommandAction commandAction;

    Command(String command, String message) {
        this.command = command;
        this.message = message;
    }

    public void setCommandAction(CommandAction commandAction) {
        this.commandAction = commandAction;
    }

    public void execute() {
        commandAction.act();
    }

    public String getCommand() {
        return command;
    }

    public static List<String> getAllCommand() {
        return Arrays.stream(values())
                .map(Command::getCommand)
                .collect(Collectors.toList());
    }

    public String getMessage() {
        return message;
    }

    public static List<String> getAllMessage() {
        return Arrays.stream(values())
                .map(Command::getMessage)
                .collect(Collectors.toList());
    }
}
