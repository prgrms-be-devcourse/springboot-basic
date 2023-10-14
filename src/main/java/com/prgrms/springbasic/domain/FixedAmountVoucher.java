package com.prgrms.springbasic.domain;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

    public FixedAmountVoucher(String discountType, long discountValue) {
        this.voucherId = UUID.randomUUID();
        this.discountType = DiscountType.find(discountType);
        this.discountValue = discountValue;
    }

    @Override
    long discount(long beforeDiscount) {
        return beforeDiscount - discountValue;
    }
}
