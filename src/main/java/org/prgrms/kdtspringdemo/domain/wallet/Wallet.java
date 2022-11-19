package org.prgrms.kdtspringdemo.domain.wallet;

import java.util.Objects;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wallet wallet = (Wallet) o;
        return Objects.equals(customerId, wallet.customerId) && Objects.equals(voucherId, wallet.voucherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, voucherId);
    }
}
