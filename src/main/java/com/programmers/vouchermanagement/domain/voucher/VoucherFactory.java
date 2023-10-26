package com.programmers.vouchermanagement.domain.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherFactory {

    private VoucherFactory() {
    }

    public static Voucher createVoucher(UUID voucherId, String voucherName, float discountAmount, LocalDateTime createAt, VoucherType voucherType) {
        return switch (voucherType) {
            case FIXED -> new FixedAmountVoucher(voucherId, voucherName, discountAmount, createAt);
            case PERCENTAGE -> new PercentDiscountVoucher(voucherId, voucherName, discountAmount, createAt);
        };
    }
}
