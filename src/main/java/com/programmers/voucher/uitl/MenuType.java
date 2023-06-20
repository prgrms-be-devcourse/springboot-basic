package com.programmers.voucher.uitl;

public enum MenuType {
    LIST("list"),
    CREATE("create"),
    EXIT("exit");

    private final String command;

    MenuType(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
