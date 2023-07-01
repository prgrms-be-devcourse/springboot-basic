package com.prgrms.model.voucher;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherCreator {

    private final String errorMessage = "지원되지 않는 바우처 정책입니다.";

    public Voucher createVoucher(Discount discount, VoucherType voucherType) {

        Voucher voucher;
        UUID id = UUID.randomUUID();

        switch (voucherType) {
            case FIXED_AMOUNT_VOUCHER -> voucher = new FixedAmountVoucher(id, discount, voucherType);
            case PERCENT_DISCOUNT_VOUCHER -> voucher = new PercentDiscountVoucher(id, discount, voucherType);
            default -> throw new IllegalArgumentException(errorMessage);
        }
        return voucher;
    }
}