package com.programmers.springweekly.dto.wallet.request;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class WalletUpdateRequest {
    private UUID walletId;
    private UUID customerId;
    private UUID voucherId;

    @Builder
    public WalletUpdateRequest(UUID walletId, UUID customerId, UUID voucherId) {
        this.walletId = walletId;
        this.customerId = customerId;
        this.voucherId = voucherId;
    }
}
