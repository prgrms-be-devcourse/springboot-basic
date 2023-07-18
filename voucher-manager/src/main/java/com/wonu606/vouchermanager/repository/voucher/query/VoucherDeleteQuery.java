package com.wonu606.vouchermanager.repository.voucher.query;

public class VoucherDeleteQuery {

    private final String voucherId;

    public VoucherDeleteQuery(String voucherId) {
        this.voucherId = voucherId;
    }

    public String getVoucherId() {
        return voucherId;
    }
}
