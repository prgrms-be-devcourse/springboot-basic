package com.prgrms.vouchermanagement.wallet;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class WalletResponse {

    private UUID walletId;
    private UUID voucherId;
    private UUID customerId;

    private LocalDateTime createdAt;

    private WalletResponse() {

    }

    public WalletResponse(UUID walletId, UUID customerId, UUID voucherId, LocalDateTime createdAt) {
        this.walletId = walletId;
        this.voucherId = voucherId;
        this.customerId = customerId;
        this.createdAt = createdAt;
    }

    public static WalletResponse from(Wallet wallet) {
        return new WalletResponse(wallet.getWalletId(), wallet.getCustomerId(), wallet.getVoucherId(), wallet.getCreatedAt());
    }

    public static WalletResponse getEmptyWalletDto() {
        return new WalletResponse();
    }

    public static List<WalletResponse> fromList(List<Wallet> wallets) {
        return wallets.stream().map(WalletResponse::from).collect(Collectors.toList());
    }

    public UUID getWalletId() {
        return walletId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
