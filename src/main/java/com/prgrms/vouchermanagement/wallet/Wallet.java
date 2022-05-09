package com.prgrms.vouchermanagement.wallet;

import java.time.LocalDateTime;

public class Wallet {
    private final Long walletId;
    private final Long customerId;
    private final Long voucherId;

    private final LocalDateTime  createdAt;

    public Wallet(Long walletId, Long customerId, Long voucherId, LocalDateTime createdAt) {
        this.walletId = walletId;
        this.customerId = customerId;
        this.voucherId = voucherId;
        this.createdAt = createdAt;
    }

    public static Wallet of(Long customerId, Long voucherId) {
        return new Wallet(null, customerId, voucherId, LocalDateTime.now());
    }

    public static Wallet of(Long walletId, Long customerId, Long voucherId, LocalDateTime createdAt) {
        return new Wallet(walletId, customerId, voucherId, createdAt);
    }

    public Long getWalletId() {
        return walletId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}