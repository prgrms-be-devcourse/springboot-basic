package com.prgrms.model.voucher;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherCreator {

    private final String errorMessage = "지원되지 않는 바우처 정책입니다.";

    public Voucher createVoucher(Discount discount, VoucherPolicy voucherPolicy) {

        Voucher voucher;
        UUID id = UUID.randomUUID();

        switch (voucherPolicy) {
            case FIXED_AMOUNT_VOUCHER -> voucher = new FixedAmountVoucher(id, discount, voucherPolicy);
            case PERCENT_DISCOUNT_VOUCHER -> voucher = new PercentDiscountVoucher(id, discount, voucherPolicy);
            default -> throw new IllegalArgumentException(errorMessage);
        }
        return voucher;
    }
}