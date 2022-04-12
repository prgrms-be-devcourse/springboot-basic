package org.prgrms.kdt.models;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        this.setVoucherId(voucherId);
        this.percent = percent;
    }

    @Override
    public long discount() {
        return 0;
    }
}
