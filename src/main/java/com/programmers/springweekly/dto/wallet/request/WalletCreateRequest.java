package com.programmers.springweekly.dto.wallet.request;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class WalletCreateRequest {
    private UUID customerId;
    private UUID voucherId;

    @Builder
    public WalletCreateRequest(UUID customerId, UUID voucherId) {
        this.customerId = customerId;
        this.voucherId = voucherId;
    }
}
