package com.wonu606.vouchermanager.controller.voucher.response;

import java.util.List;

public class VoucherGetResponse {

    private final List<String> voucherIds;

    public VoucherGetResponse(List<String> voucherIds) {
        this.voucherIds = voucherIds;
    }

    public List<String> getVoucherIds() {
        return voucherIds;
    }
}
