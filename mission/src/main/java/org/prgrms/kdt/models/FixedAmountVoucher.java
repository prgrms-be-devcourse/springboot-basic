package org.prgrms.kdt.models;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.setVoucherId(voucherId);
        this.amount = amount;
    }

    @Override
    public long discount() {
        return 0;
    }
}
