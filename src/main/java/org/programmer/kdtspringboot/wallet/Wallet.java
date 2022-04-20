package org.programmer.kdtspringboot.wallet;

import org.programmer.kdtspringboot.customer.Customer;
import org.programmer.kdtspringboot.voucher.Voucher;

import java.util.UUID;

public class Wallet {
    private UUID walletId;
    private UUID customerId;
    private UUID voucherId;

    public Wallet(UUID walletId, UUID customerId, UUID voucherId) {
        this.walletId = walletId;
        this.customerId = customerId;
        this.voucherId = voucherId;
    }

    public UUID getWalletId() {
        return walletId;
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
                "walletId=" + walletId +
                ", customerId=" + customerId +
                ", voucherId=" + voucherId +
                '}';
    }
}
