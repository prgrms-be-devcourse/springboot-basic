package com.wonu606.vouchermanager.controller.voucherwallet.reqeust;

public class OwnedCustomersRequest {

    private final String voucherId;

    public OwnedCustomersRequest(String voucherId) {
        this.voucherId = voucherId;
    }

    public String getVoucherId() {
        return voucherId;
    }
}
