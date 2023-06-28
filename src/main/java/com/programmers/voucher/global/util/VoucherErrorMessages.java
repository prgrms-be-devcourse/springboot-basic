package com.programmers.voucher.global.util;

public interface VoucherErrorMessages {
    String UNHANDLED_VOUCHER_TYPE = "Voucher type is unhandled";
    String INVALID_VOUCHER_TYPE = "Voucher type is invalid";
    String INVALID_FIXED_AMOUNT = "Fixed amount must be positive";
    String INVALID_PERCENT_DISCOUNT = "Percent discount must be between 0 and 100";
}
