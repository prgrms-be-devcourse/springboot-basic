package com.wonu606.vouchermanager.controller.customer.response;

public class OwnedVoucherResponse {

    private final String voucherId;

    public OwnedVoucherResponse(String voucherId) {
        this.voucherId = voucherId;
    }

    public String getVoucherId() {
        return voucherId;
    }
}
