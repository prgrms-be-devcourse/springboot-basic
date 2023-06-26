package com.programmers.voucher.domain;

public abstract class Discount {

    private final long value;

    public Discount(long value) {
        this.value = value;
    }

    public static Discount of(VoucherType voucherType, long value) {
        switch (voucherType) {
            case FIXED :
                return new FixedDiscount(value);
            case PERCENT :
                return new PercentDiscount(value);
        }
        throw new IllegalArgumentException("");
    }

    public long getValue() {
        return value;
    }

    public abstract VoucherType getVoucherType();

    public abstract long applyDiscount(long beforeDiscount);
}
