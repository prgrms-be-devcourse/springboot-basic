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

    @Override
    public UUID getUUID() {
        return this.voucherId;
    }

    @Override
    public String toString() {
        return "퍼센트 할인권, 할인비율 : [" + percent + "%]";
    }
}
