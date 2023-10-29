package com.programmers.vouchermanagement.message;

public enum ErrorMessage {
    INVALID_COMMAND_MESSAGE("[System] Invalid command."),
    INVALID_UUID("[System] Invalid UUID."),

    // Voucher Error Message
    INVALID_FIXED_DISCOUNT_AMOUNT("[System] Fixed discount amount must be greater than 0"),
    INVALID_PERCENTAGE_DISCOUNT_AMOUNT("[System] Percent discount amount must be less than or equal to 100"),
    DISCOUNT_OVER_BEFORE_DISCOUNT("[System] Discount amount is greater than before discount amount."),
    VOUCHER_ALREADY_EXISTS_MESSAGE("[System] Voucher already exists."),
    VOUCHER_NOT_FOUND_MESSAGE("[System] Voucher not found."),

    // Customer Error Message
    CUSTOMER_ALREADY_EXISTS_MESSAGE("[System] Customer already exists."),
    CUSTOMER_NOT_FOUND_MESSAGE("[System] Customer not found."),

    // Wallet Error Message
    WALLET_NOT_FOUND_MESSAGE("[System] Wallet not found."),
    // File Error Message
    FILE_NOT_FOUND_MESSAGE("[System] File not found.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
