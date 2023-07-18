package com.example.voucher.controller.request;

import java.util.UUID;

public class WalletRequest {

    public static class Create {

        private UUID customerId;
        private UUID voucherId;

        public Create(UUID customerId, UUID voucherId) {
            this.customerId = customerId;
            this.voucherId = voucherId;
        }

        public UUID getCustomerId() {
            return customerId;
        }

        public UUID getVoucherId() {
            return voucherId;
        }

    }

    public static class Update {

        private UUID walletId;
        private UUID customerId;
        private UUID voucherId;

        public Update(UUID walletId, UUID customerId, UUID voucherId) {
            this.walletId = walletId;
            this.customerId = customerId;
            this.voucherId = voucherId;
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

}
