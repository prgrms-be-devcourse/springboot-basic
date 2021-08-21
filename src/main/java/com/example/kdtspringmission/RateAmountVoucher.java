package com.example.kdtspringmission;

public class RateAmountVoucher implements Voucher {

    private final long percent;

    public RateAmountVoucher(long percent) {
        this.percent = percent;
    }

    public RateAmountVoucher() {
        this(10L);
    }

    @Override
    public long discountPrice(long price) {

        return (long) (price - (price * ((double)percent / 100)));
    }
}
