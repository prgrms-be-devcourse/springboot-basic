package org.programers.vouchermanagement.wallet.dto.response;

import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
public class WalletViewResponse {

    private UUID voucherId;
    private UUID memberId;

    public WalletViewResponse(UUID voucherId, UUID memberId) {
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
