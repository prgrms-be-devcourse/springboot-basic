package com.waterfogsw.voucher.console;

public enum CommandMenu {
    CREATE("Type create to create a new voucher."),
    LIST("Type list to list all vouchers."),
    BLACKLIST("Type blacklist to list black list."),
    EXIT("Type exit to exit the program.");

    private final String message;

    CommandMenu(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
