package com.wonu606.vouchermanager.controller.customer.request;

public class CustomerGetOwnedVouchersRequest {

    private final String customerId;

    public CustomerGetOwnedVouchersRequest(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }
}
