package com.wonu606.vouchermanager.controller.customer.request;

public class WalletDeleteRequest {

    private final String voucherId;
    private final String customerId;

    public WalletDeleteRequest(String voucherId, String customerId) {
        this.voucherId = voucherId;
        this.customerId = customerId;
    }

    public String getVoucherId() {
        return voucherId;
    }

    public String getCustomerId() {
        return customerId;
    }
}
