package org.prgms.kdtspringweek1.wallet.entity;

import java.util.UUID;

public class Wallet {
    private final UUID walletId;
    private final UUID voucherId;
    private final UUID customerId;

    public static Wallet createWithVoucherIdAndCustomerId(UUID voucherId, UUID customerId) {
        return new Wallet(voucherId, customerId);
    }

    public static Wallet createWithWalledIdAndVoucherIdAndCustomerId(UUID walletId, UUID voucherId, UUID customerId) {
        return new Wallet(walletId, voucherId, customerId);
    }

    private Wallet(UUID walletId, UUID voucherId, UUID customerId) {
        this.walletId = walletId;
        this.voucherId = voucherId;
        this.customerId = customerId;
    }

    private Wallet(UUID voucherId, UUID customerId) {
        this.walletId = UUID.randomUUID();
        this.voucherId = voucherId;
        this.customerId = customerId;
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
}
