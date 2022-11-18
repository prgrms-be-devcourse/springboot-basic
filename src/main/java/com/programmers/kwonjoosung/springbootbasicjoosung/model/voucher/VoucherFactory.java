package com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher;

import java.util.UUID;

public class VoucherFactory {

    private VoucherFactory(){

    }

    public static Voucher createVoucher(VoucherType voucherType, UUID voucherId, long discount) {
        return switch (voucherType) {
            case FIXED -> new FixedAmountDiscountVoucher(voucherId,discount);
            case PERCENT -> new PercentDiscountVoucher(voucherId,discount);
        };
    }

    public static Voucher createVoucher(VoucherType voucherType, long discount) {
        return createVoucher(voucherType,UUID.randomUUID(),discount);
    }
}
