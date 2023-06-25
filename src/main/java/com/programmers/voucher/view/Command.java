package com.programmers.voucher.view;

public enum Command {
    EXIT("exit", "to exit the program."),
    CREATE("create", "to create a new voucher."),
    LIST("list", "to list all vouchers.");

    private final String value;
    private final String text;

    Command(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
