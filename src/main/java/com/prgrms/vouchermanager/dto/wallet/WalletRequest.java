package com.prgrms.vouchermanager.dto.wallet;

import lombok.Builder;

import java.util.UUID;

public class WalletRequest {
    public record WalletDetailRequest(UUID customerId, UUID voucherId) {
        @Builder
        public WalletDetailRequest {
        }
    }
}
