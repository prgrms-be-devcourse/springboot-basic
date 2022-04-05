package org.prgms.springbootbasic.management.entity;

import java.util.UUID;

public class PercentAmountVoucher implements Voucher {
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
}
