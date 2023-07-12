package com.programmers.springweekly.domain.voucher;

import java.util.UUID;

public class VoucherFactory {

    public static Voucher createVoucher(UUID voucherId, VoucherType voucherType, long discount) {

        return switch (voucherType) {
            case FIXED -> new FixedAmountVoucher(voucherId, discount);
            case PERCENT -> new PercentDiscountVoucher(voucherId, discount);
        };
    }
    
}
