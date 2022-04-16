package com.prgrms.kdt.springbootbasic.entity;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher{
    private UUID voucherId;
    private long discountPercentage;

    public PercentDiscountVoucher(UUID voucherId, long discountPercentage){
        this.voucherId = voucherId;
        this.discountPercentage = discountPercentage;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getDiscountedMoney(long beforeDiscount) {

        return beforeDiscount * (100 - discountPercentage) / 100;
    }

    @Override
    public long getDiscountAmount() {
        return discountPercentage;
    }
}
