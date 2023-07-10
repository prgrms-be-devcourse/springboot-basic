package org.programmers.VoucherManagement.voucher.domain;

import java.util.UUID;

public abstract class Voucher {
    protected UUID voucherId;
    protected DiscountValue discountValue;
    protected DiscountType discountType;

    public DiscountType getDiscountType() {
        return discountType;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public DiscountValue getDiscountValue() {
        return discountValue;
    }

    public void changeDiscountValue(DiscountValue discountValue) {
        this.discountValue = discountValue;
    }

}
