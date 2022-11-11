package com.programmers.VoucherManagementApplication.io;

public enum Message {
    MENU_PROMPT("\n========= Voucher Program =========\n" +
            "Type exit to exit the program.\n" +
            "Type create to create a new voucher.\n" +
            "Type list to list all vouchers.\n"),
    INVALID_INPUT("\nThe input value is not valid. Please try again.\n"),
    EXIT_MENU("\nExit Voucher Program\n"),

    VOUCHER_MENU("\n1. FixedAmountVoucher \n2. PercentDiscountVoucher\n"),

    PRICE_INPUT_PROMPT("\nAmount before discount"),
    FIXED_VOUCHER_INPUT_PROMPT("\nA fixed discount"),
    PERCENT_VOUCHER_INPUT_PROMPT("\nA percent discount");

    private final String message;

    Message(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
