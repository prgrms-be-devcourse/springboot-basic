package com.wonu606.vouchermanager.controller.customer.response;

import java.util.List;

public class CustomerGetOwnedVouchersResponse {

    private final List<String> voucherIds;

    public CustomerGetOwnedVouchersResponse(List<String> voucherId) {
        this.voucherIds = voucherId;
    }

    public List<String> getVoucherIds() {
        return voucherIds;
    }
}
