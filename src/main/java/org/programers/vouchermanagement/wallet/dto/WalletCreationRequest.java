package org.programers.vouchermanagement.wallet.dto;

import java.util.UUID;

public class WalletCreationRequest {

    private final UUID voucherId;
    private final UUID memberId;

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
