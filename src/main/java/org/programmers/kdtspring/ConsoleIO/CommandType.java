package org.programmers.kdtspring.ConsoleIO;

public enum CommandType {
    EXIT("exit"),
    CREATE("create"),
    LIST("list"),
    APPEND("append"),
    LIST_FOR_CUSTOMER("listForCustomer");

    private final String command;

    CommandType(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}