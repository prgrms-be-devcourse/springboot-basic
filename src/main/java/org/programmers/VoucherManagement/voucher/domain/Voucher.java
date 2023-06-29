package org.programmers.VoucherManagement.voucher.domain;

import java.util.UUID;

public abstract class Voucher {
    private UUID voucherId;
    private DiscountType discountType;
    private int discountValue;

    public Voucher(UUID voucherId, DiscountType discountType, int discountValue) {
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

    public int getDiscountValue() {
        return discountValue;
    }

    abstract long calculateDiscountPrice(long beforeDiscountPrice);
}
