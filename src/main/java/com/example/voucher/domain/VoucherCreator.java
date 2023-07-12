package com.example.voucher.domain;

import java.util.UUID;
import com.example.voucher.constant.VoucherType;

public class VoucherCreator {

    private VoucherCreator() {
    }

    public static Voucher getVoucher(UUID voucherId, VoucherType voucherType, Long discountValue) {
        return switch (voucherType) {
            case FIXED_AMOUNT_DISCOUNT -> new FixedAmountVoucher(voucherId, discountValue);
            case PERCENT_DISCOUNT -> new PercentDiscountVoucher(voucherId, discountValue);
        };
    }
}
