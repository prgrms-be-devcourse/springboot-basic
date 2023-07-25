package com.wonu606.vouchermanager.repository.voucherwallet.resultset;

public class OwnedVoucherResultSet {

    private final String voucherId;

    public OwnedVoucherResultSet(String voucherId) {
        this.voucherId = voucherId;
    }

    public String getVoucherId() {
        return voucherId;
    }
}
