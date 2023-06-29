package org.programmers.VoucherManagement.voucher.domain;

import java.util.UUID;

public abstract class Voucher {
    private UUID voucherId;
    private DiscountType discountType;
    private DiscountValue discountValue;

    public Voucher(UUID voucherId, DiscountType discountType, DiscountValue discountValue) {
        this.voucherId = voucherId;
        this.discountType = discountType;
        this.discountValue = discountValue;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public DiscountValue getDiscountValue() {
        return discountValue;
    }

    abstract long calculateDiscountPrice(long beforeDiscountPrice);
}
