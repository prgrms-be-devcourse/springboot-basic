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
    PRINT_VOUCHER_MESSAGE_FORM("""
            Voucher Type   => {0}
            Voucher ID     => {1}
            Discount Value => {2}
            Created Date   => {3}
            """),
    VOUCHER_DISCOUNT_VALUE_MESSAGE("Please enter a number for creating voucher.");

    private final String message;

    ConsoleMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
