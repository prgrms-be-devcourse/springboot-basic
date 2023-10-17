package com.programmers.vouchermanagement.domain.voucher;

import java.util.UUID;

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

    public static Voucher createVoucher(UUID voucherId, String voucherName, float discountAmount, VoucherType voucherType) {
        switch (voucherType) {
            case FIXED:
                return new FixedAmountVoucher(voucherId, voucherName, discountAmount);
            case PERCENTAGE:
                return new PercentDiscountVoucher(voucherId, voucherName, discountAmount);
            default:
                throw new IllegalArgumentException("Unknown VoucherType: " + voucherType);
        }
    }
}
