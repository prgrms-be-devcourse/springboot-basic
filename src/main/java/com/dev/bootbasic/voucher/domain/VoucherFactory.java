package com.dev.bootbasic.voucher.domain;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherFactory {

    public Voucher createVoucher(UUID id, String type, int discountAmount) {
        VoucherType voucherType = VoucherType.from(type);

        return switch (voucherType) {
            case FIXED -> FixedAmountVoucher.of(id, discountAmount);
            case PERCENT -> PercentDiscountVoucher.of(id, discountAmount);
        };
    }

}
