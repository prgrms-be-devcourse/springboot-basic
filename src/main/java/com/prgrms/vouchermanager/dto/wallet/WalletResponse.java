package com.prgrms.vouchermanager.dto.wallet;

import com.prgrms.vouchermanager.domain.wallet.Wallet;
import lombok.Builder;

import java.util.UUID;

public class WalletResponse {
    public record WalletDetailResponse(UUID walletId, UUID customerId, UUID voucherId) {
        @Builder
        public WalletDetailResponse {
        }
    }

    public static WalletDetailResponse toDetailWallet(Wallet wallet) {
        return WalletDetailResponse.builder()
                .walletId(wallet.getWalletId())
                .customerId(wallet.getCustomerId())
                .voucherId(wallet.getVoucherId())
                .build();
    }
}
