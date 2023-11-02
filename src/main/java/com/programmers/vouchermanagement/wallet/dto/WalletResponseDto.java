package com.programmers.vouchermanagement.wallet.dto;

import java.util.UUID;

public class WalletResponseDto {

    private final UUID walletId;
    private final UUID customerId;
    private final UUID voucherId;


    public WalletResponseDto(UUID walletId, UUID customerId, UUID voucherId) {
        this.walletId = walletId;
        this.customerId = customerId;
        this.voucherId = voucherId;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }
}
