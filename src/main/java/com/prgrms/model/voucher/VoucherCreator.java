package com.prgrms.model.voucher;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherCreator {

    public Voucher createVoucher(Discount discount, VoucherType voucherType) {

        UUID id = getVoucherID();

        return switch (voucherType) {
            case FIXED_AMOUNT_VOUCHER -> new FixedAmountVoucher(id, discount, voucherType);
            case PERCENT_DISCOUNT_VOUCHER -> new PercentDiscountVoucher(id, discount, voucherType);
        };
    }

    public UUID getVoucherID() {
        return UUID.randomUUID();
    }
}