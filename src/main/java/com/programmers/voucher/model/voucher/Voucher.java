package com.programmers.voucher.model.voucher;

import com.programmers.voucher.model.customer.Customer;

import java.util.UUID;

public abstract class Voucher {
    protected String voucherId;
    protected UUID voucherNumber;
    protected long discountValue;
    protected Customer customer;

    protected Voucher(UUID voucherNumber, long discountValue) {
        validateZeroDiscount(discountValue);
        this.voucherNumber = voucherNumber;
        this.discountValue = discountValue;
    }

    abstract void validateZeroDiscount(long discountValue);

    abstract long discount(long beforeDiscount);

    public String getVoucherId(){
        return voucherId;
    }

    public UUID getVoucherNumber() {
        return voucherNumber;
    }

    public long getDiscountValue() {
        return discountValue;
    }
}