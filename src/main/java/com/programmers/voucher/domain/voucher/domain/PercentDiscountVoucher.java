package com.programmers.voucher.domain.voucher.domain;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        super(voucherId);
        this.percent = percent;
    }

    @Override
    public long totalAmount(long beforeAmount) {
        return beforeAmount - beforeAmount * percent / 100;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public String fullInfoString() {
        return "VoucherID: " + voucherId + ", discount: " + percent + "%";
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "voucherId=" + voucherId +
                ", percent=" + percent +
                '}';
    }
}
