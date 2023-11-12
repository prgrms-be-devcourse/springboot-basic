package com.prgrms.springbasic.domain.wallet.dto;

import com.prgrms.springbasic.domain.wallet.entity.Wallet;

import java.util.UUID;

public record WalletResponse(UUID walletId, UUID customerId, UUID voucherId) {
    public static WalletResponse from(Wallet wallet) {
        return new WalletResponse(
                wallet.getWalletId(),
                wallet.getCustomerId(),
                wallet.getVoucherId()
        );
    }
}
