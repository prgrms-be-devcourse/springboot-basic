package com.wonu606.vouchermanager.repository.voucherwallet.query;

public class WalletInsertQuery {

    private final String voucherId;

    public WalletInsertQuery(String voucherId) {
        this.voucherId = voucherId;
    }

    public String getVoucherId() {
        return voucherId;
    }
}
