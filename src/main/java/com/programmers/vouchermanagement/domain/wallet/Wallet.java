package com.programmers.vouchermanagement.domain.wallet;

import java.util.UUID;

public class Wallet {
    private final UUID id;
    private final UUID customerId;
    private final UUID voucherId;

    public Wallet(UUID id, UUID customerId, UUID voucherId) {
        this.id = id;
        this.customerId = customerId;
        this.voucherId = voucherId;
    }

    public UUID getId() {
        return id;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }
}
