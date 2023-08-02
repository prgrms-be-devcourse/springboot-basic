package com.prgrms.wallet.model;

public class Wallet {

    private final String customerId;
    private final String voucherId;
    private final String walletId;
    private boolean deleted = false;

    public Wallet(String walletId, String customerId, String voucherId) {
        this.walletId = walletId;
        this.customerId = customerId;
        this.voucherId = voucherId;
    }

    public boolean markAsDeleted() {
        return deleted = true;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getVoucherId() {
        return voucherId;
    }

    public String getWalletId() {
        return walletId;
    }

    public boolean isDeleted() {
        return deleted;
    }

}
