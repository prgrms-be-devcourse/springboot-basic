package org.prgrms.kdtspringhomework.voucher.domain;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private static final long ZERO_VOUCHER_AMOUNT = 0;
    private static final long MAX_VOUCHER_AMOUNT = 10000;

    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        validationPercent(percent);
        this.voucherId = voucherId;
        this.percent = percent;
    }

    private void validationPercent(long percent) {
        if (percent < ZERO_VOUCHER_AMOUNT) {
            throw new IllegalArgumentException("Percent should be positive");
        }
        if (percent == ZERO_VOUCHER_AMOUNT) {
            throw new IllegalArgumentException("Percent should not be zero");
        }
        if (percent > MAX_VOUCHER_AMOUNT) {
            throw new IllegalArgumentException(String.format("Percent should be less than %d", MAX_VOUCHER_AMOUNT));
        }
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return (long) (beforeDiscount * ((double) percent / 100));
    }

    @Override
    public String toString() {
        return "percent," +
                voucherId +
                "," + percent;
    }
}
