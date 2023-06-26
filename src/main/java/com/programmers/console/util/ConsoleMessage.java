package com.programmers.console.util;

public enum ConsoleMessage {
    COMMAND_MESSAGE("""
            === Voucher Program ===
            Type **exit** to exit the program.
            Type **create** to create a new voucher.
            Type **list** to list all vouchers.
            """),
    CREATE_VOUCHER_TYPE_MESSAGE("""
            Select a voucher type.
            1 : FIXED TYPE
            2 : PERCENT TYPE
            """),
    VOUCHER_DISCOUNT_VALUE_MESSAGE("Please enter a value for creating voucher."),
    LIST_IS_EMPTY("List is empty. Try again after make vouchers.");

    private final String message;

    ConsoleMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
