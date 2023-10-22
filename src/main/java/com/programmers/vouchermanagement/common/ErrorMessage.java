package com.programmers.vouchermanagement.common;

public enum ErrorMessage {
    INVALID_COMMAND_MESSAGE("[System] Invalid command."),
    INVALID_FIXED_DISCOUNT_AMOUNT("[System] Fixed discount amount must be greater than 0"),
    INVALID_PERCENTAGE_DISCOUNT_AMOUNT("[System] Percent discount amount must be less than or equal to 100"),
    DISCOUNT_OVER_BEFORE_DISCOUNT("[System] Discount amount is greater than before discount amount."),
    VOUCHER_CREATE_FAILED("[System] Voucher create failed."),
    FILE_NOT_FOUND_MESSAGE("[System] File not found.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
