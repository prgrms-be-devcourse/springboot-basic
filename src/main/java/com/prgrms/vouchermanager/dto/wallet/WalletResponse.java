package com.prgrms.vouchermanager.dto.wallet;

import lombok.Builder;

import java.util.UUID;

public class WalletResponse {
    public record WalletDetailResponse(UUID walletId, UUID customerId, UUID voucherId) {
        @Builder
        public WalletDetailResponse {
        }
    }
}
