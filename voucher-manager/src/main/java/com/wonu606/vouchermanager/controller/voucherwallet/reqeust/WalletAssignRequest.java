package com.wonu606.vouchermanager.controller.voucherwallet.reqeust;

public class WalletAssignRequest {

    private final String voucherId;

    public WalletAssignRequest(String voucherId) {
        this.voucherId = voucherId;
    }

    public String getVoucherId() {
        return voucherId;
    }
}
