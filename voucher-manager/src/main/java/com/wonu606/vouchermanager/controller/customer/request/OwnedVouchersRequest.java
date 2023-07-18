package com.wonu606.vouchermanager.controller.customer.request;

public class OwnedVouchersRequest {

    private final String customerId;

    public OwnedVouchersRequest(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }
}
