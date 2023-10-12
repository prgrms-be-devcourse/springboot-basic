package org.prgrms.kdtspringdemo.voucher.domain;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher{
    private UUID voucherId;
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
    public long discount(long beforeDiscount) {
        return 0;
    }
}
