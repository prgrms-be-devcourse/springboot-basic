package com.wonu606.vouchermanager.controller.voucher.response;

import java.util.List;

public class VoucherResponse {

    private final List<String> voucherIds;

    public VoucherResponse(List<String> voucherIds) {
        this.voucherIds = voucherIds;
    }

    public List<String> getVoucherIds() {
        return voucherIds;
    }
}
