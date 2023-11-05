package com.prgrms.vouchermanagement.core.voucher.controller.response;

import java.util.List;

public class VouchersResponse {

    private List<VoucherResponse> voucherResponses;

    public VouchersResponse() {
    }

    public VouchersResponse(List<VoucherResponse> voucherResponses) {
        this.voucherResponses = voucherResponses;
    }

    public List<VoucherResponse> getVoucherResponses() {
        return voucherResponses;
    }
}
