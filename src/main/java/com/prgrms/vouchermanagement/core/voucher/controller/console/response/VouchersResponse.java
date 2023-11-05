package com.prgrms.vouchermanagement.core.voucher.controller.response;

import java.util.List;

public class VouchersResponse {

    private final List<VoucherResponse> voucherResponses;

    public VouchersResponse(List<VoucherResponse> voucherResponses) {
        this.voucherResponses = voucherResponses;
    }

    public List<VoucherResponse> getVoucherResponses() {
        return voucherResponses;
    }
}
