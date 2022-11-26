package com.programmers.VoucherManagementApplication.io;

public enum Message {

    MENU_PROMPT("\n========= Voucher Program =========\n" +
            "Type exit to exit the program.\n" +
            "Type create to create a new voucher.\n" +
            "Type list to list all vouchers.\n"),
    EXIT_MESSAGE("\nExit the program.\n"),
    CREATE_MENU("\n1. FixedAmountVoucher \n2. PercentDiscountVoucher\n" +
            "Enter fixed/percent, and enter discount values separated by 'spaces'\n" +
            "ex) fixed 10\n"),
    NO_LIST("\nNo saved voucher\n"),

    INVALID_INPUT("\nThe input value is not valid. Please try again.\n"),
    INVALID_REQUEST_SIZE("\nThe request value is not in the correct format.\n"),
    INVALID_ZERO("\nAmount should not be zero"),
    INVALID_MINUS("\nAmount should be positive"),
    INVALID_INTEGER("\nThe price is an integer."),

    INVALID_FIXED_MAX("\nDiscount value is greater than maximum discount value. Please try again.\n"),
    INVALID_PERCENT("\nThe percent input value is not valid.\n");

    private final String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
