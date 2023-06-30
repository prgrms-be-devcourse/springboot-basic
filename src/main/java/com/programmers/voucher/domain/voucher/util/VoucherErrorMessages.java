package com.programmers.voucher.domain.voucher.util;

public final class VoucherErrorMessages {
    public static final String UNHANDLED_VOUCHER_TYPE = "Voucher type is unhandled";
    public static final String INVALID_VOUCHER_TYPE = "Voucher type is invalid";
    public static final String INVALID_FIXED_AMOUNT = "Fixed amount must be positive";
    public static final String INVALID_PERCENT_DISCOUNT = "Percent discount must be between 0 and 100";

    private VoucherErrorMessages() {
    }
}
