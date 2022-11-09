package com.programmers.voucher.voucher;

import java.util.UUID;

public class VoucherFactory {
    public static Voucher createVoucher(UUID id, VoucherType type, long value) {
        if (type == VoucherType.FixedAmount) {
            return new FixedAmountVoucher(id, value);
        }

        return new PercentDiscountVoucher(id, value);
    }

    public static Voucher createVoucher(VoucherType type, long value) {
        return VoucherFactory.createVoucher(UUID.randomUUID(), type, value);
    }
}