package org.promgrammers.springbootbasic.domain.voucher.model;

import java.util.UUID;

public abstract class Voucher {

    private final UUID voucherId;
    private final long amount;

    public abstract VoucherType getVoucherType();

    public abstract long discount(long beforeDiscount);

    public UUID getVoucherId() {
        return voucherId;
    }

    protected Voucher(UUID voucherId, long amount) {
        validateAmount(amount);
        this.voucherId = voucherId;
        this.amount = amount;
    }

    protected abstract void validateAmount(long discountAmount);

    public long getAmount() {
        return amount;
    }
}
