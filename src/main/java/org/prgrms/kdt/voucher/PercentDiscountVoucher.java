package org.prgrms.kdt.voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private static final long MAX_VOUCHER_PERCENT = 100;

    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        if (percent < 0) throw new IllegalArgumentException("Percent should be positive");
        if (percent == 0) throw new IllegalArgumentException("Percent should not be zero");
        if (percent > MAX_VOUCHER_PERCENT)
            throw new IllegalArgumentException(String.format("Percent should be less than %d", MAX_VOUCHER_PERCENT));
        this.voucherId = voucherId;
        this.percent = percent;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / MAX_VOUCHER_PERCENT);
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher" +
                "," + voucherId +
                "," + percent;
    }
}