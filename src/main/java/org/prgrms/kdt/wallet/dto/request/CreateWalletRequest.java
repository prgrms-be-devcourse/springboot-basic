package org.prgrms.kdt.wallet.dto.request;

import java.util.UUID;

public record CreateWalletRequest(UUID walletId, UUID memberId, UUID voucherId) {
    public CreateWalletRequest(UUID memberId, UUID voucherId) {
        this(UUID.randomUUID(), memberId, voucherId);
    }
}
