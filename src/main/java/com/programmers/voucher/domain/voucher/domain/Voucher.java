package com.programmers.voucher.domain.voucher.domain;

import java.util.UUID;

public abstract class Voucher {
    protected final UUID voucherId;

    public Voucher(UUID voucherId) {
        this.voucherId = voucherId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public abstract long totalAmount(long beforeAmount);

    public abstract String fullInfoString();
}
