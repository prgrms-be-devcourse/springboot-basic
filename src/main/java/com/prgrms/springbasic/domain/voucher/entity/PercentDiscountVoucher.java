package com.prgrms.springbasic.domain.voucher.entity;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {
    public PercentDiscountVoucher(String discountType, long discountValue) {
        this.voucherId = UUID.randomUUID();
        this.discountType = DiscountType.find(discountType);
        this.discountValue = discountValue;
    }

    public PercentDiscountVoucher(UUID voucherId, String discountType, long discountValue) {
        this.voucherId = voucherId;
        this.discountType = DiscountType.find(discountType);
        this.discountValue = discountValue;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (discountValue / 100);
    }
}
