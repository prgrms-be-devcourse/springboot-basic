package org.devcourse.springbasic.domain.voucher.domain;

import java.util.UUID;

public abstract class Voucher {

    private final UUID voucherId;
    private final long discountRate;
    private final VoucherType voucherType;

    protected Voucher(UUID voucherId, long discountRate, VoucherType voucherType) {
        this.voucherId = voucherId;
        this.discountRate = discountRate;
        this.voucherType = voucherType;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getDiscountRate() {
        return discountRate;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }


    public abstract long discount(long beforeDiscount);
    public abstract long maxDiscountRate();
}
