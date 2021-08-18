package com.example.kdtspringmission;

public class FixedAmountVoucher implements Voucher{

    private final long amount;

    public FixedAmountVoucher(long amount) {
        this.amount = amount;
    }

    public FixedAmountVoucher() {
        this(100L);
    }

    @Override
    public long discountPrice(long price) {
        return price - amount;
    }
}
