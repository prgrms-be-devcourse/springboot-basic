package org.prgrms.vouchermanager.wallet;

import java.util.UUID;

public class Wallet {
    private final UUID walletId;
    private final UUID voucherId;
    private final UUID customerId;

    public Wallet(UUID walletId, UUID customerId, UUID voucherId) {
        if (walletId == null) throw new IllegalArgumentException("walletId should not be null");
        if (customerId == null) throw new IllegalArgumentException("customerId should not be null");
        if (voucherId == null) throw new IllegalArgumentException("voucherId should not be null");
        this.walletId = walletId;
        this.customerId = customerId;
        this.voucherId = voucherId;
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
