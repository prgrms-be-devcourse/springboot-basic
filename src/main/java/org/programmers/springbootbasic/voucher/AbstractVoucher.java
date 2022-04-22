package org.programmers.springbootbasic.voucher;

import java.util.UUID;

public abstract class AbstractVoucher implements Voucher {

    private UUID id;
    private int amount;
    private VoucherType type;

    public AbstractVoucher(UUID id, int amount, VoucherType type) {
        this.id = id;
        this.amount = amount;
        this.type = type;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public VoucherType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractVoucher)) return false;

        AbstractVoucher that = (AbstractVoucher) o;

        if (amount != that.amount) return false;
        if (!id.equals(that.id)) return false;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + amount;
        result = 31 * result + type.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AbstractVoucher{" +
                "type=" + type +
                ", id=" + id +
                ", amount=" + amount +
                '}';
    }
}
