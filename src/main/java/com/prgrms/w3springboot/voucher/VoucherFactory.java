package com.prgrms.w3springboot.voucher;

import java.util.UUID;

public class VoucherFactory {
    public static Voucher createVoucher(VoucherType voucherType, long discountAmount) {
        switch (voucherType) {
            case FIXED:
                return new FixedAmountVoucher(UUID.randomUUID(), discountAmount);
            case PERCENT:
                return new PercentAmountVoucher(UUID.randomUUID(), discountAmount);
            default:
                throw new IllegalArgumentException("잘못된 타입을 입력받았습니다.");
        }
    }
}
