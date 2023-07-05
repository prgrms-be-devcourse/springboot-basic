package com.programmers.voucher.domain.voucher.util;

public final class VoucherErrorMessages {
    public static final String UNHANDLED_VOUCHER_TYPE = "Voucher type is unhandled";
    public static final String INVALID_VOUCHER_TYPE = "Voucher type is invalid: Current input {0}";
    public static final String INVALID_FIXED_AMOUNT = "Fixed amount must be positive: Current input {0}";
    public static final String INVALID_PERCENT_DISCOUNT = "Percent discount must be between 0 and 100: Current input {0}";
    public static final String NO_SUCH_VOUCHER = "Voucher does not exist: VoucherId {0}";

    private VoucherErrorMessages() {
    }
}
