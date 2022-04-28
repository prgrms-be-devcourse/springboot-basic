package com.prgrms.vouchermanagement.wallet;

import java.util.UUID;

public class WalletRequest {

    private UUID customerId;
    private UUID voucherId;

    public WalletRequest(UUID customerId, UUID voucherId) {
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
