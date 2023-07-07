package com.programmers.springweekly.dto.voucher.response;

import java.util.List;

public class VoucherListResponse {

    private List<VoucherResponse> voucherResponseList;

    public VoucherListResponse(List<VoucherResponse> voucherResponseList) {
        this.voucherResponseList = voucherResponseList;
    }
}
