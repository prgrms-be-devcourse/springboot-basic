package org.programmers.VoucherManagement.voucher.domain;

import java.util.UUID;

public abstract class Voucher {
    protected UUID voucherId;
    protected DiscountValue discountValue;
    protected DiscountType discountType;

    public Voucher(UUID voucherId, DiscountType discountType, DiscountValue discountValue) {
        this.voucherId = voucherId;
        this.discountValue = discountValue;
        this.discountType = discountType;
    }

    abstract public DiscountType getDiscountType();

    abstract public UUID getVoucherId();

    abstract public DiscountValue getDiscountValue();

}
