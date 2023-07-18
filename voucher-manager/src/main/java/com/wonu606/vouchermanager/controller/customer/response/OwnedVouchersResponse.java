package com.wonu606.vouchermanager.controller.customer.response;

import java.util.List;

public class OwnedVouchersResponse {

    private final List<String> voucherIds;

    public OwnedVouchersResponse(List<String> voucherId) {
        this.voucherIds = voucherId;
    }

    public List<String> getVoucherIds() {
        return voucherIds;
    }
}
