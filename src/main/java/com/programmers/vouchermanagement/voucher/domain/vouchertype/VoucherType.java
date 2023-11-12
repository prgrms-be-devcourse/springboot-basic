package com.programmers.vouchermanagement.voucher.domain.vouchertype;

public abstract class VoucherType {
    protected static final long MIN_DISCOUNT_VALUE = 0;

    abstract public void validateDiscountValue(long discountValue);

    abstract public String getName();
}
