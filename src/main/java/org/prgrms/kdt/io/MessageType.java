package org.prgrms.kdt.io;

public enum MessageType {
    CONSOLE_START("=== Voucher Program ===\n" +
            "Type 'exit' to exit the program.\n" +
            "Type 'create' to create a new voucher.\n" +
            "Type 'list' to list all vouchers."),
    SELECT_WRONG("Wrong Selection. Please write again."),
    CONSOLE_END("Exit Program. Good Bye.");

    private final String message;

    MessageType(String message) {
        this.message = message;
    }


    public String getMessage() {
        return message;
    }
}
