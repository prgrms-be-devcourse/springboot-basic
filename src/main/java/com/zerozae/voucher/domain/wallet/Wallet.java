package com.zerozae.voucher.domain.wallet;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Wallet {

    private final UUID customerId;
    private final UUID voucherId;

    public Wallet(UUID customerId, UUID voucherId) {
        this.customerId = customerId;
        this.voucherId = voucherId;
    }
}
