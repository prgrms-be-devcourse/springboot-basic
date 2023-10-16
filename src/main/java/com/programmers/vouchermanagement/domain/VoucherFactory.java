package com.programmers.vouchermanagement.domain;

public class VoucherFactory {
    public static Voucher createVoucher(String voucherName, float discountAmount, VoucherType voucherType) {
        switch (voucherType) {
            case FIXED:
                return new FixedAmountVoucher(voucherName, discountAmount);
            case PERCENTAGE:
                return new PercentDiscountVoucher(voucherName, discountAmount);
            default:
                throw new IllegalArgumentException("Unknown VoucherType: " + voucherType);
        }
    }
}
