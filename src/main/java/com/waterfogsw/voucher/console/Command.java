package com.waterfogsw.voucher.console;

public enum Command {
    CREATE("create"),
    LIST("list"),
    BLACKLIST("blacklist"),
    EXIT("exit");

    private final String command;

    Command(String command) {
        this.command = command;
    }
}
