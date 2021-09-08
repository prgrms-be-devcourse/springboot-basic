package org.prgrms.dev.voucher.domain;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private static final long MAX_VOUCHER_PERCENT = 100;
    private static final long ZERO_AMOUNT = 0;
    private static final long PERCENTAGE = 100;

    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        if (percent < ZERO_AMOUNT) throw new IllegalArgumentException("Amount should be positive");
        if (percent == ZERO_AMOUNT) throw new IllegalArgumentException("Amount should not be zero");
        if (percent > MAX_VOUCHER_PERCENT) throw new IllegalArgumentException("Amount should be less than " + MAX_VOUCHER_PERCENT);

        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / PERCENTAGE);
    }

    @Override
    public String toString() {
        return "percent:"
                + voucherId +
                ":" + percent;
    }
}
