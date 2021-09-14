package com.prgrms.w3springboot.voucher;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherFactory {
    public Voucher createVoucher(UUID voucherId, VoucherType voucherType, long discountAmount) {
        switch (voucherType) {
            case FIXED:
                return new FixedAmountVoucher(voucherId, discountAmount);
            case PERCENT:
                return new PercentAmountVoucher(voucherId, discountAmount);
            default:
                throw new IllegalArgumentException("잘못된 타입을 입력받았습니다.");
        }
    }
}
