package com.waterfogsw.voucher.voucher.domain;

public class FixedAmountVoucher extends Voucher {

    private final int amount;

    public FixedAmountVoucher(int amount) {
        this(null, amount);
    }

    public FixedAmountVoucher(Long id, int amount) {
        super(id, VoucherType.FIXED_AMOUNT);
        validate(amount);
        this.amount = amount;
    }

    @Override
    public int getValue() {
        return amount;
    }

    @Override
    public int discount(int before) {
        return Math.max(before - amount, 0);
    }

    @Override
    protected void validate(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException();
        }
    }
}
