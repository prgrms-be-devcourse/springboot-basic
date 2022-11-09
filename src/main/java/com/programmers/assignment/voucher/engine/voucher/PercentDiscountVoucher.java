package com.programmers.assignment.voucher.engine.voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private static final long MAX_VOUCHER_PERCENT = 100;
    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        if (percent < 0) throw new IllegalArgumentException("Discount percent should be positive");
        if (percent == 0) throw new IllegalArgumentException("Discount percent shoud not be zero");
        if (percent >= MAX_VOUCHER_PERCENT) throw new IllegalArgumentException(String.format("Discount percent shoud be less than %d", MAX_VOUCHER_PERCENT));
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent/100);
    }
}
