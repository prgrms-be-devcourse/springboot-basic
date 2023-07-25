package com.wonu606.vouchermanager.repository.voucherwallet.query;

public class OwnedVouchersQuery {

    private final String customerId;

    public OwnedVouchersQuery(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }
}
