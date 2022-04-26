package org.prgrms.vouchermanager.domain.wallet.domain;

import java.util.UUID;

/**
 * customer와 voucher 다대다 연관관계를
 */
public class Wallet {
    private final UUID walletId;
    private final UUID voucherId;
    private final UUID customerId;

    public Wallet(UUID walletId, UUID customerId, UUID voucherId) {
        if (walletId == null) throw new IllegalArgumentException("walletId는 null이 될 수 없습니다.");
        if (customerId == null) throw new IllegalArgumentException("customerId는 null이 될 수 없습니다.");
        if (voucherId == null) throw new IllegalArgumentException("voucherId는 null이 될 수 없습니다.");
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
