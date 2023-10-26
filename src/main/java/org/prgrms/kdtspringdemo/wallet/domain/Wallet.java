package org.prgrms.kdtspringdemo.wallet.domain;

import java.util.UUID;
import java.util.List;

public class Wallet {
    private final UUID walletId;
    private final UUID customerId;
    private final List<UUID> vouchers;

    public Wallet(UUID walletId, UUID customerId, List<UUID> vouchers) {
        this.walletId = walletId;
        this.customerId = customerId;
        this.vouchers = vouchers;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public List<UUID> getVouchers() {
        return vouchers;
    }

    public String toString() {
        return "=======================\n" +
                "[walletId] : " + walletId + "\n" +
                "[customerId] : " + customerId + "\n" +
                "[voucherId List] : \n" +
                "   total count : " + vouchers.size() + "\n" +
                "   " + vouchers.stream().toString();
    }
}
