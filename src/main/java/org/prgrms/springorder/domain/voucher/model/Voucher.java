package org.prgrms.springorder.domain.voucher.model;

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

    protected abstract void validateAmount(long amount);

    public long getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return String.format("voucherType = %s, id = %s, amount = %s", getVoucherType(), getVoucherId(), getAmount());
    }

}
