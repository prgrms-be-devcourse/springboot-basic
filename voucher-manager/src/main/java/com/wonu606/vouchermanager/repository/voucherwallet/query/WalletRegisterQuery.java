package com.wonu606.vouchermanager.repository.voucherwallet.query;

public class WalletRegisterQuery {

    private final String customerId;
    private final String voucherId;

    public WalletRegisterQuery(String customerId, String voucherId) {
        this.customerId = customerId;
        this.voucherId = voucherId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getVoucherId() {
        return voucherId;
    }
}
