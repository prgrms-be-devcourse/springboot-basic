package com.dev.bootbasic.voucher.domain;

import java.util.UUID;

public class VoucherFactory {

    public static Voucher create(UUID id, String type, int discountAmount) {
        VoucherType voucherType = VoucherType.from(type);

        return switch (voucherType) {
            case FIXED -> FixedAmountVoucher.of(id, discountAmount);
            case PERCENT -> PercentDiscountVoucher.of(id, discountAmount);
        };
    }
}
