package org.prgrms.kdt.wallet.controller.dto;

import java.util.UUID;

public record ControllerCreateWalletRequest(UUID walletId, UUID memberId, UUID voucherId) {
    public ControllerCreateWalletRequest(UUID memberId, UUID voucherId) {
        this(UUID.randomUUID(), memberId, voucherId);
    }
}
