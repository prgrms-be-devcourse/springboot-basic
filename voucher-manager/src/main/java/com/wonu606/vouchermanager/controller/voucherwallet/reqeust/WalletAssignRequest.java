package com.wonu606.vouchermanager.controller.voucherwallet.reqeust;

public class WalletAssignRequest {

    private String voucherId;

    public WalletAssignRequest() {
    }

    public WalletAssignRequest(String voucherId) {
        this.voucherId = voucherId;
    }

    public String getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }
}
