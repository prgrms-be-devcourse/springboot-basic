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
}
