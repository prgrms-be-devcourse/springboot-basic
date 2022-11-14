package com.programmers.voucher.model.voucher;

import java.util.UUID;

public abstract class Voucher {
    protected UUID voucherId;
    protected long discountValue;

    protected Voucher(UUID voucherId, long discountValue) {
        validateZeroDiscount(discountValue);
        this.voucherId = voucherId;
        this.discountValue = discountValue;
    }

    abstract void validateZeroDiscount(long discountValue);

    abstract long discount(long beforeDiscount);

    public UUID getVoucherId(){
        return voucherId;
    }
}