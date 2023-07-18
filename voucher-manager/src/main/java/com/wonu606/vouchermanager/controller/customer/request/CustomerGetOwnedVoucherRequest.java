package com.wonu606.vouchermanager.controller.customer.request;

public class CustomerGetOwnedVoucherRequest {

    private final String customerId;

    public CustomerGetOwnedVoucherRequest(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }
}
