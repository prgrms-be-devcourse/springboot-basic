package org.prgrms.springorder.domain.voucher_wallet.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherWallet {

    private final UUID walletId;

    private final UUID customerId;

    private final UUID voucherId;

    private final LocalDateTime createdAt;

    public VoucherWallet(UUID walletId, UUID customerId, UUID voucherId, LocalDateTime createdAt) {
        this.walletId = walletId;
        this.customerId = customerId;
        this.voucherId = voucherId;
        this.createdAt = createdAt;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public static VoucherWallet create(UUID walletId, UUID customerId, UUID voucherId) {
        return new VoucherWallet(walletId, customerId, voucherId, LocalDateTime.now());
    }
}
