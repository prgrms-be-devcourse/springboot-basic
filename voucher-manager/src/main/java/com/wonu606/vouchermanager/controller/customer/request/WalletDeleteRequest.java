package com.wonu606.vouchermanager.controller.customer.request;

public class WalletDeleteRequest {

    private String voucherId;
    private String customerId;

    public WalletDeleteRequest() {
    }

    public WalletDeleteRequest(String voucherId, String customerId) {
        this.voucherId = voucherId;
        this.customerId = customerId;
    }

    public String getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
