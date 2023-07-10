package me.kimihiqq.vouchermanagement.domain.voucherwallet;

import java.util.Objects;
import java.util.UUID;


public class VoucherWallet {
    private UUID customerId;
    private UUID voucherId;

    public VoucherWallet(UUID customerId, UUID voucherId) {
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
        VoucherWallet that = (VoucherWallet) o;
        return Objects.equals(customerId, that.customerId) &&
                Objects.equals(voucherId, that.voucherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, voucherId);
    }

}
