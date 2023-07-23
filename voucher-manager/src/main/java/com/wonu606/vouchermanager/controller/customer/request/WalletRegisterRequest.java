package com.wonu606.vouchermanager.controller.customer.request;

public class WalletRegisterRequest {

    private final String voucherId;
    private final String customerId;

    public WalletRegisterRequest(String voucherId, String customerId) {
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
