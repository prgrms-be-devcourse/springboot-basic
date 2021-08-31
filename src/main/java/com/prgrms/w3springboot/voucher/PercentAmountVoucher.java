package com.prgrms.w3springboot.voucher;

import java.util.UUID;

public class PercentAmountVoucher implements Voucher {
    public static final int DENOMINATOR_FOR_PECENT = 100;
    private final UUID voucherId;
    private final long percent;

    public PercentAmountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getAmount() {
        return percent;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / DENOMINATOR_FOR_PECENT);
    }

    @Override
    public String toString() {
        return "PercentAmountVoucher{" +
                "voucherId=" + voucherId +
                ", percent=" + percent +
                '}';
    }
}
