package com.programmers.voucher.domain;

public abstract class Discount {

    private final VoucherType voucherType;
    private final long value;

    protected Discount(VoucherType voucherType, long value) {
        this.voucherType = voucherType;
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

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public long getValue() {
        return value;
    }

    public abstract long applyDiscount(long beforeDiscount);
}
