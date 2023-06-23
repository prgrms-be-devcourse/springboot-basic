package org.devcourse.voucher.domain.voucher;

import org.devcourse.voucher.domain.voucher.amount.VoucherAmount;

public abstract class Voucher {
    private final long id;
    private final VoucherType type;
    protected final VoucherAmount amount;

    protected Voucher(long id, VoucherType type, VoucherAmount amount) {
        this.id = id;
        this.type = type;
        this.amount = amount;
    }

    public static Voucher of(VoucherType type, int amount) {
        return VoucherFactory.of(type, amount);
    }

    public long getId() {
        return id;
    }

    public VoucherType getType() {
        return type;
    }

    public VoucherAmount getAmount() {
        return amount;
    }

    public abstract Money retrievePostBalance(Money money);
}
