package com.programmers.voucher;

public enum ConsoleMessage {
    COMMAND_MESSAGE("=== Voucher Program ===\n" +
            "Type **exit** to exit the program.\n" +
            "Type **create** to create a new voucher.\n" +
            "Type **list** to list all vouchers."),
    CREATE_VOUCHER_TYPE_MESSAGE("Select a voucher type\n" +
            "1:FixedAmountVoucher 2:PercentDiscountVoucher");

    private final String message;

    ConsoleMessage(String message) {
        this.message = message;
    }
}
