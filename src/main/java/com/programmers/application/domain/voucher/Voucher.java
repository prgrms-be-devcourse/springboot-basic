package com.programmers.application.domain.voucher;

import java.util.UUID;

public abstract class Voucher {
    private final UUID voucherId;

    protected Voucher(UUID voucherId) {
        this.voucherId = voucherId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public abstract long discount(long originalPrice);

    public abstract long getDiscountAmount();
}
