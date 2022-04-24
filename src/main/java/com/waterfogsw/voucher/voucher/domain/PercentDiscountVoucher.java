package com.waterfogsw.voucher.voucher.domain;

public class PercentDiscountVoucher extends Voucher {

    private final int percent;

    public PercentDiscountVoucher(int percent) {
        super(null, VoucherType.PERCENT_DISCOUNT);
        validate(percent);
        this.percent = percent;
    }

    public PercentDiscountVoucher(Long id, int percent) {
        super(id, VoucherType.PERCENT_DISCOUNT);
        validate(percent);
        this.percent = percent;
    }

    @Override
    public int getValue() {
        return percent;
    }

    @Override
    public int discount(int before) {
        int restPercent = 100 - percent;
        return before * restPercent / 100;
    }

    @Override
    protected void validate(int percent) {
        if (!(percent > 0 && percent <= 100)) {
            throw new IllegalArgumentException();
        }
    }
}
