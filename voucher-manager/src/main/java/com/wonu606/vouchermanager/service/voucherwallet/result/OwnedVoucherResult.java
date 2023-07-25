package com.wonu606.vouchermanager.service.voucherwallet.result;

public class OwnedVoucherResult {

    private final String voucherUuid;

    public OwnedVoucherResult(String voucherUuid) {
        this.voucherUuid = voucherUuid;
    }

    public String getVoucherUuid() {
        return voucherUuid;
    }
}
