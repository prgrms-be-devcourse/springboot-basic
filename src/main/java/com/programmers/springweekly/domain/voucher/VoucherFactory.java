package com.programmers.springweekly.domain.voucher;

import java.util.UUID;

public class VoucherFactory {

    public static Voucher createVoucher(UUID voucherId, VoucherType voucherType, long discount) {

        switch (voucherType) {
            case FIXED -> {
                return new FixedAmountVoucher(voucherId, discount);
            }
            case PERCENT -> {
                return new PercentDiscountVoucher(voucherId, discount);
            }
            default -> throw new IllegalArgumentException("Input: " + voucherType + "바우처 타입이 없습니다.");
        }
    }
    
}
