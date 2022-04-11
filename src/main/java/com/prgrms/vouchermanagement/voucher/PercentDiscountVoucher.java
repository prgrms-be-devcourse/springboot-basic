package com.prgrms.vouchermanagement.voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher{

    private UUID voucherId;
    private long discountPercentage;

    public PercentDiscountVoucher(UUID voucherId, long discountPercentage) {
        this.voucherId = voucherId;
        this.discountPercentage = discountPercentage;
    }

    @Override
    public long discount(long beforeDiscountPrice) {
        return beforeDiscountPrice * (discountPercentage / 100);
    }

    @Override
    public UUID getId() {
        return voucherId;
    }

    @Override
    public String toString() {
        return "Id=" + getId() + ", Type=PercentDiscount, discountPercent=" + discountPercentage + "%";
    }

    @Override
    public long getAmount() {
        return discountPercentage;
    }
}
