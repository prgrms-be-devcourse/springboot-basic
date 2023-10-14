package com.prgrms.springbasic.domain;

import java.util.UUID;

public abstract class Voucher {

    protected UUID voucherId;
    protected DiscountType discountType;
    protected long discountValue;

    abstract long discount(long beforeDiscount);

    public UUID getVoucherId() {
        return voucherId;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public long getDiscountValue() {
        return discountValue;
    }
}
