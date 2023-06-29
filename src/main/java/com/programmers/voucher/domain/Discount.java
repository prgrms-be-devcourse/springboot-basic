package com.programmers.voucher.domain;

public abstract class Discount {

    private final VoucherType voucherType;

    protected Discount(VoucherType voucherType) {
        this.voucherType = voucherType;
    }

    public static Discount of(VoucherType voucherType, long value) {
        return switch (voucherType) {
            case FIXED -> new FixedDiscount(value);
            case PERCENT -> new PercentDiscount(value);
        };
    }

    public abstract long applyDiscount(long beforeDiscount);

    public abstract long getAmount();

    public VoucherType getVoucherType() {
        return voucherType;
    }
}
