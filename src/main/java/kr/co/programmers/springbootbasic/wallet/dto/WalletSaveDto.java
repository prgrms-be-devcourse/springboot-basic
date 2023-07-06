package kr.co.programmers.springbootbasic.wallet.dto;

import java.util.UUID;

public class WalletSaveDto {
    private final UUID voucherId;
    private final UUID walletId;

    public WalletSaveDto(UUID voucherId, UUID walletId) {
        this.voucherId = voucherId;
        this.walletId = walletId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public UUID getWalletId() {
        return walletId;
    }
}
