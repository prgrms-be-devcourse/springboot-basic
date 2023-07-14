package com.programmers.voucher.domain.voucher.domain;

import com.programmers.voucher.domain.voucher.pattern.VoucherVisitor;

import java.util.UUID;

public abstract class Voucher {
    protected final UUID voucherId;

    public Voucher(UUID voucherId) {
        this.voucherId = voucherId;
    }

    public abstract void accept(VoucherVisitor visitor);

    public UUID getVoucherId() {
        return voucherId;
    }

    public abstract long totalAmount(long beforeAmount);
}
