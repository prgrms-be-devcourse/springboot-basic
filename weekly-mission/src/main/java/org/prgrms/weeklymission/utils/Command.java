package org.prgrms.weeklymission.utils;

public enum Command {
    EXIT("exit"), CREATE("create"), LIST("list"),
    BLACK_REGISTER("register"), BLACK_LIST("blacks");

    private final String value;
    Command(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
