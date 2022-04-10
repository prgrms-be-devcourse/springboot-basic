package org.prgrms.kdt.models;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher{
    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return null;
    }

    @Override
    public long discount() {
        return 0;
    }
}
