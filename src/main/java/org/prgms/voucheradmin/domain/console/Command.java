package org.prgms.voucheradmin.domain.console;

public enum Command {
    EXIT("exit"), CREATE("create"), LIST("list"), BLACKLIST("blacklist");

    private final String command;

    Command(String command) {
        this.command = command;
    }
}
