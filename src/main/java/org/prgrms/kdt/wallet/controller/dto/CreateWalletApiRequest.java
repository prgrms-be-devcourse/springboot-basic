package org.prgrms.kdt.wallet.controller.dto;

import java.util.UUID;

public record CreateWalletApiRequest(UUID walletId, UUID memberId, UUID voucherId) {
    public CreateWalletApiRequest(UUID memberId, UUID voucherId) {
        this(UUID.randomUUID(), memberId, voucherId);
    }
}
