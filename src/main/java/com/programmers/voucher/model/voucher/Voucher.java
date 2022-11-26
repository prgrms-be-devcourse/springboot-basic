package com.programmers.voucher.model.voucher;

import com.programmers.voucher.model.customer.Customer;

import java.util.UUID;

public abstract class Voucher {
    protected UUID voucherId;
    protected long discountValue;
    protected Customer customer;

    protected Voucher(UUID voucherId, long discountValue) {
        validateDiscountRange(discountValue);
        this.voucherId = voucherId;
        this.discountValue = discountValue;
    }

    abstract void validateDiscountRange(long discountValue);

    abstract long discount(long beforeDiscount);

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getDiscountValue() {
        return discountValue;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setDiscountValue(long discountValue) {
        validateDiscountRange(discountValue);
        this.discountValue = discountValue;
    }
}