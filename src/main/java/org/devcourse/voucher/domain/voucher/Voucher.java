package org.devcourse.voucher.domain.voucher;

public abstract class Voucher {
    private final long id;
    private final VoucherType type;

    protected Voucher(long id, VoucherType type) {
        this.id = id;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public VoucherType getType() {
        return type;
    }

    public abstract Money retrievePostBalance(Money money);
}
