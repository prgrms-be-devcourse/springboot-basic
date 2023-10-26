package org.prgrms.kdtspringdemo.wallet.domain.dto;

import java.util.UUID;

public class WalletDBDto {
    private final UUID walletId;
    private final UUID customerId;

    public WalletDBDto(UUID walletId, UUID customerId) {
        this.walletId = walletId;
        this.customerId = customerId;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public UUID getCustomerId() {
        return customerId;
    }
}
