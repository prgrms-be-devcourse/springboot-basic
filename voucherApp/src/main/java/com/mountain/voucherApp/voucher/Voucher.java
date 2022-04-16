package com.mountain.voucherApp.voucher;

import com.mountain.voucherApp.enums.DiscountPolicy;

import java.util.UUID;

public abstract class Voucher {
    public abstract long discount(long beforeDiscount, long discountAmount);
    public abstract boolean validate(long discountAmount) throws IllegalArgumentException;
}
