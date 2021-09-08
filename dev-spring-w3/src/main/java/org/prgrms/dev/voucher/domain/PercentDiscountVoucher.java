package org.prgrms.dev.voucher.domain;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private static final int MAX_PERCENT = 100;
    private static final int ZERO_PERCENT = 0;
    private static final int PERCENTAGE = 100;

    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        if (percent < ZERO_PERCENT) {
            throw new IllegalArgumentException("Percent should be positive");
        }
        if (percent == ZERO_PERCENT) {
            throw new IllegalArgumentException("Percent should not be zero");
        }
        if (percent > MAX_PERCENT) {
            throw new IllegalArgumentException(String.format("Percent should be less than %d ", MAX_PERCENT));
        }

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
