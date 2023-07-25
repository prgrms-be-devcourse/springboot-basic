package com.wonu606.vouchermanager.repository.voucherwallet.resultset;

public class OwnedCustomerResultSet {

    private final String customerId;

    public OwnedCustomerResultSet(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }
}
