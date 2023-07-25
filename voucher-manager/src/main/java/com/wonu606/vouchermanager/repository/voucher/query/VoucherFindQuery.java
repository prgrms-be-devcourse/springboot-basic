package com.wonu606.vouchermanager.repository.voucher.query;

public class VoucherFindQuery {

    private final String voucherId;

    public VoucherFindQuery(String voucherId) {
        this.voucherId = voucherId;
    }

    public String getVoucherId() {
        return voucherId;
    }
}
