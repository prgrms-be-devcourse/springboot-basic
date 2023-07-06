package org.programers.vouchermanagement.wallet.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class WalletCreationRequest {

    @NotNull
    private UUID voucherId;

    @NotNull
    private UUID memberId;

    public WalletCreationRequest(UUID voucherId, UUID memberId) {
        this.voucherId = voucherId;
        this.memberId = memberId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public UUID getMemberId() {
        return memberId;
    }
}
