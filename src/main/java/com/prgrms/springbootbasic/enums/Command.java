package com.prgrms.springbootbasic.enums;

public enum Command {
    EXIT("exit"),
    CREATE("create"),
    LIST("list");
    private final String command;

    Command(String command) {
        this.command = command;
    }
}
