package com.example.voucher.wallet.controller;

import java.util.UUID;

public class WalletRequest {

    private UUID walletId;
    private UUID customerId;
    private UUID voucherId;

    private WalletRequest(UUID walletId, UUID customerId, UUID voucherId) {
        this.walletId = walletId;
        this.customerId = customerId;
        this.voucherId = voucherId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private UUID walletId;
        private UUID customerId;
        private UUID voucherId;

        public Builder setWalletId(UUID walletId) {
            this.walletId = walletId;

            return this;
        }

        public Builder setCustomerId(UUID customerId) {
            this.customerId = customerId;

            return this;
        }

        public Builder setVoucherId(UUID voucherId) {
            this.voucherId = voucherId;

            return this;
        }

        public WalletRequest build() {
            return new WalletRequest(walletId, customerId, voucherId);
        }

    }

    public UUID getWalletId() {
        return walletId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }
    
}
