package org.prgrms.kdt.wallet;

import java.util.UUID;

public class Wallet {
    private final String walletId;
    private final String customerId;
    private final String voucherId;

    private Wallet(String customerId, String voucherId) {
        this.walletId = UUID.randomUUID().toString();
        this.customerId = customerId;
        this.voucherId = voucherId;
    }

    public Wallet(String walletId, String customerId, String voucherId) {
        this.walletId = walletId;
        this.customerId = customerId;
        this.voucherId = voucherId;
    }

    public String getWalletId() {
        return walletId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getVoucherId() {
        return voucherId;
    }
}
