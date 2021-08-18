package org.prgrms.kdt.voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final long percent;

    protected PercentDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    @Override
    public String toString() {
        return "[PercentDiscountVoucher] voucherId: " + voucherId + ", percent: " + percent + "\n";
    }
}
