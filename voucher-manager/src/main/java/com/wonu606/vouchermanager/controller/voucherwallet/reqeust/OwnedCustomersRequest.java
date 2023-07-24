package com.wonu606.vouchermanager.controller.voucherwallet.reqeust;

public class OwnedCustomersRequest {

    private String voucherId;

    public OwnedCustomersRequest() {
    }

    public OwnedCustomersRequest(String voucherId) {
        this.voucherId = voucherId;
    }

    public String getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }
}
