package com.prgrms.vouchermanagement.voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher{

    private UUID voucherId;
    private long discountPercentage;

    public PercentDiscountVoucher(long discountPercentage) {
        this.voucherId = UUID.randomUUID();
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
}
