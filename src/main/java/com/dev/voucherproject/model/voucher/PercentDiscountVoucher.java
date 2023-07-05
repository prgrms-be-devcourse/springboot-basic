package com.dev.voucherproject.model.voucher;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, VoucherPolicy voucherPolicy, long percent) {
        super(voucherId, voucherPolicy);
        this.percent = percent;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    @Override
    public long getDiscountFigure() {
        return this.percent;
    }
}
