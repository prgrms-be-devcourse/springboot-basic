package org.prgrms.kdt.wallet.domain;

import java.util.UUID;

public class Wallet {
    private final UUID walletId;
    private UUID memberId;
    private UUID voucherId;

    public Wallet(UUID walletId, UUID memberId, UUID voucherId) {
        this.walletId = walletId;
        this.memberId = memberId;
        this.voucherId = voucherId;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public UUID getMemberId() {
        return memberId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }
}
