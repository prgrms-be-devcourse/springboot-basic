package org.prgms.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher{
    private final long amount;
    private final UUID voucherId;

    public FixedAmountVoucher(long amount, UUID voucherId) {
        this.amount = amount;
        this.voucherId = voucherId;
    }

    @Override
    public long apply(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }
}
