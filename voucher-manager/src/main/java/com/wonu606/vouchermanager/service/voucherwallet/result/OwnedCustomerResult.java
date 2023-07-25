package com.wonu606.vouchermanager.service.voucherwallet.result;

public class OwnedCustomerResult {

    private final String customerEmail;

    public OwnedCustomerResult(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }
}
