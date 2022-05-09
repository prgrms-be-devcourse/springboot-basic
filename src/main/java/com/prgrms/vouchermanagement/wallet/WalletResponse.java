package com.prgrms.vouchermanagement.wallet;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class WalletResponse {

    private Long walletId;
    private Long voucherId;
    private Long customerId;
    private LocalDateTime createdAt;


    public WalletResponse(Long walletId, Long voucherId, Long customerId, LocalDateTime createdAt) {
        this.walletId = walletId;
        this.voucherId = voucherId;
        this.customerId = customerId;
        this.createdAt = createdAt;
    }

    public static WalletResponse from(Wallet wallet) {
        return new WalletResponse(wallet.getWalletId(), wallet.getCustomerId(), wallet.getVoucherId(), wallet.getCreatedAt());
    }

    public static List<WalletResponse> fromList(List<Wallet> wallets) {
        return wallets.stream().map(WalletResponse::from).collect(Collectors.toList());
    }

    public Long getWalletId() {
        return walletId;
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
