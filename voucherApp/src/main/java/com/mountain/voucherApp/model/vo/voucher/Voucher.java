package com.mountain.voucherApp.model.vo.voucher;

public abstract class Voucher {
    public abstract long discount(long beforeDiscount, long discountAmount);
    public abstract boolean validate(long discountAmount) throws IllegalArgumentException;
}
