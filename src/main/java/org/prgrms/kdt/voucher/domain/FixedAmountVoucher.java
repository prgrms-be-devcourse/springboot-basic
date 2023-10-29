package org.prgrms.kdt.voucher.domain;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long amount;
    private UUID customerId;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public String toString() {
        return String.format("FixedAmountVoucher    '{voucherId = %-40s, amount = %s}'", voucherId, amount);
    }
}
