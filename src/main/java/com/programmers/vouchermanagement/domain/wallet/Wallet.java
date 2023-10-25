package com.programmers.vouchermanagement.domain.wallet;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Wallet {
    private Integer id;
    private final UUID customerId;
    private final UUID voucherId;
    private boolean used;

    public Wallet(int id, UUID customerId, UUID voucherId, boolean used) {
        this.id = id;
        this.customerId = customerId;
        this.voucherId = voucherId;
        this.used = used;
    }

    public Wallet(UUID customerId, UUID voucherId) {
        this.customerId = customerId;
        this.voucherId = voucherId;
        this.used = false;
    }
}
