package com.wonu606.vouchermanager.repository.voucherwallet.query;

public class OwnedCustomersQuery {

    private final String voucherId;

    public OwnedCustomersQuery(String voucherId) {
        this.voucherId = voucherId;
    }

    public String getVoucherId() {
        return voucherId;
    }
}
