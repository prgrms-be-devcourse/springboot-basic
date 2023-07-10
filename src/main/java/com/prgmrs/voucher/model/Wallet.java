package com.prgmrs.voucher.model;

import java.util.UUID;

public class Wallet {
    private final UUID userId;
    private final UUID voucherId;

    public Wallet(UUID userId, UUID voucherId) {
        this.userId = userId;
        this.voucherId = voucherId;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }
}
