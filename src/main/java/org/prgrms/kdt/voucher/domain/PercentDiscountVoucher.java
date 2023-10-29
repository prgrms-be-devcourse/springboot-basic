package org.prgrms.kdt.voucher.domain;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final int percent;
    private UUID customerId;

    public PercentDiscountVoucher(UUID voucherId, int percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    @Override
    public String toString() {
        return String.format("PercentDiscountVoucher'{voucherId = %-40s, percent = %s}'", voucherId, percent);
    }
}
