package com.wonu606.vouchermanager.controller.customer.request;

public class OwnedVouchersRequest {

    private String customerId;

    public OwnedVouchersRequest() {
    }

    public OwnedVouchersRequest(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
