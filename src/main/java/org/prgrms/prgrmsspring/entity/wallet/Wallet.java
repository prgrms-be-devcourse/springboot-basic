package org.prgrms.prgrmsspring.entity.wallet;

import java.util.UUID;

public class Wallet {

    private final UUID customerId;
    private final UUID voucherId;

    public Wallet(UUID customerId, UUID voucherId) {
        this.customerId = customerId;
        this.voucherId = voucherId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }


    @Override
    public String toString() {
        return "Wallet{" +
                "customerId=" + customerId +
                ", voucherId=" + voucherId +
                '}';
    }
}
