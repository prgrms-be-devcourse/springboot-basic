package com.wonu606.vouchermanager.controller.voucherwallet.response;

public class VoucherResponse {

    private final String voucherUuid;

    public VoucherResponse(String voucherUuid) {
        this.voucherUuid = voucherUuid;
    }

    public String getVoucherUuid() {
        return voucherUuid;
    }
}
