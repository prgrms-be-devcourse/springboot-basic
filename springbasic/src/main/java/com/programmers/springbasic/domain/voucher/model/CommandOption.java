package com.programmers.springbasic.domain.voucher.model;

public enum CommandOption {
    EXIT("exit"), CREATE("create"), LIST("list");

    String command;

    CommandOption(String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return command;
    }
}
