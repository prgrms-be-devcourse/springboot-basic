package com.prgrms.wallet.model;

public class Wallet {

    private final int customerId;
    private final int voucherId;
    private final int walletId;
    private boolean deleted = false;

    public Wallet(int walletId, int customerId, int voucherId) {
        this.walletId = walletId;
        this.customerId = customerId;
        this.voucherId = voucherId;
    }

    public boolean markAsDeleted() {
        return deleted = true;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getVoucherId() {
        return voucherId;
    }

    public int getWalletId() {
        return walletId;
    }

    public boolean isDeleted() {
        return deleted;
    }

}
