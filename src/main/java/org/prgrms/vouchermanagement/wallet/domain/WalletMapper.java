package org.prgrms.vouchermanagement.wallet.domain;

import java.util.UUID;

public class WalletMapper {
    private final UUID customerId;
    private final UUID voucherId;

    public WalletMapper(UUID customerId, UUID voucherId) {
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
