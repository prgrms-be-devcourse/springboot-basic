package com.prgms.VoucherApp.domain;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public long discount(long beforeAmount) {
        double discountAmount = beforeAmount * (percent / 100.0);
        return (long) (beforeAmount - discountAmount);
    }
}
