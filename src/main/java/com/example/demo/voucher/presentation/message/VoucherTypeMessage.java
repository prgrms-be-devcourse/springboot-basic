package com.example.demo.voucher.presentation.message;

public enum VoucherTypeMessage {
    FIXED_AMOUNT_VOUCHER("Enter the fixed amount for the voucher:"),
    PERCENT_DISCOUNT_VOUCHER("Enter the percent discount for the voucher:");

    private final String message;

    VoucherTypeMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}