package org.prgrms.kdt.wallet.controller.dto;

import java.util.UUID;

public record CreateWalletControllerRequest(UUID walletId, UUID memberId, UUID voucherId) {
    public CreateWalletControllerRequest(UUID memberId, UUID voucherId) {
        this(UUID.randomUUID(), memberId, voucherId);
    }
}
