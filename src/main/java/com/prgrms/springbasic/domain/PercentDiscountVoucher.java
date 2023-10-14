package com.prgrms.springbasic.domain;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {
    public PercentDiscountVoucher(String discountType, long discountValue) {
        this.voucherId = UUID.randomUUID();
        this.discountType = DiscountType.find(discountType);
        this.discountValue = discountValue;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - beforeDiscount * (discountValue / 100);
    }
}
