package com.mountain.voucherApp.enums;

public enum Menu {

    EXIT("exit", "to exit the program."),
    CREATE("create", "to create a new voucher."),
    LIST("list", "to list all vouchers.");

    private final String value;
    private final String description;

    Menu(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
