package org.prgrms.prgrmsspring.entity.voucher;

import java.util.Objects;
import java.util.UUID;

public abstract class Voucher {
    protected final UUID voucherId;
    protected final long amount;
    protected final String type;

    protected Voucher(UUID voucherId, long amount, String type) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.type = type;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Voucher voucher = (Voucher) object;
        return amount == voucher.amount && Objects.equals(voucherId, voucher.voucherId) && Objects.equals(type, voucher.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherId, amount, type);
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    protected abstract long discount(long beforeDiscount);
}
