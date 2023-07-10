package com.prgmrs.voucher.model;

import java.util.UUID;

public class Assignment {
    private final UUID userId;
    private final UUID voucherId;

    public Assignment(UUID userId, UUID voucherId) {
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
