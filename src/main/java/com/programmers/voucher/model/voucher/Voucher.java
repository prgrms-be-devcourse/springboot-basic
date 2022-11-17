package com.programmers.voucher.model.voucher;

import com.programmers.voucher.model.customer.Customer;

import java.util.UUID;

public abstract class Voucher {
    protected UUID voucherId;
    protected long discountValue;
    protected Customer customer;

    protected Voucher(UUID voucherId, long discountValue) {
        validateZeroDiscount(discountValue);
        this.voucherId = voucherId;
        this.discountValue = discountValue;
    }

    abstract void validateZeroDiscount(long discountValue);

    abstract long discount(long beforeDiscount);

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getDiscountValue() {
        return discountValue;
    }
}