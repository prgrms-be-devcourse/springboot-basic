package com.programmers.springweekly.dto.wallet.request;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

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
