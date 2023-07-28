package org.programmers.VoucherManagement.voucher.domain;

import org.programmers.VoucherManagement.global.entity.BaseTimeEntity;

public abstract class Voucher extends BaseTimeEntity {
    protected String voucherId;
    protected DiscountValue discountValue;
    protected DiscountType discountType;

    public DiscountType getDiscountType() {
        return discountType;
    }

    public String getVoucherId() {
        return voucherId;
    }

    public DiscountValue getDiscountValue() {
        return discountValue;
    }

    public void changeDiscountValue(DiscountValue discountValue) {
        this.discountValue = discountValue;
    }
}
