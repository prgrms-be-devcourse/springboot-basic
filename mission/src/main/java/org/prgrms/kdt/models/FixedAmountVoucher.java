package org.prgrms.kdt.models;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher{
    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
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
