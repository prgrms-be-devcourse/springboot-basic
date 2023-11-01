package org.programmers.springorder.dto.wallet;

import java.util.UUID;

public class WalletRequestDto {
    private final UUID customerId;
    private final UUID voucherId;

    public WalletRequestDto(UUID customerId, UUID voucherId) {
        this.customerId = customerId;
        this.voucherId = voucherId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }
}
