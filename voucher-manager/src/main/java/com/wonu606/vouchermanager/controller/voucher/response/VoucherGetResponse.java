package com.wonu606.vouchermanager.controller.voucher.response;

import java.util.List;

public class VoucherGetResponse {

    private final List<String> vouchers;

    public VoucherGetResponse(List<String> vouchers) {
        this.vouchers = vouchers;
    }

    public List<String> getVouchers() {
        return vouchers;
    }
}
