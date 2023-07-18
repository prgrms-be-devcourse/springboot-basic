package com.wonu606.vouchermanager.controller.voucher.reqeust;

public class VoucherGetOwnedCustomersRequest {

    private final String voucherId;

    public VoucherGetOwnedCustomersRequest(String voucherId) {
        this.voucherId = voucherId;
    }

    public String getVoucherId() {
        return voucherId;
    }
}
