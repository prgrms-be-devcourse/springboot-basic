package org.promgrammers.springbootbasic.dto.response;

import java.util.List;

public class VoucherListResponse {
    private final List<VoucherResponse> voucherResponseList;

    public VoucherListResponse(List<VoucherResponse> voucherResponseList) {
        this.voucherResponseList = voucherResponseList;
    }

    public List<VoucherResponse> getVoucherResponseList() {
        return voucherResponseList;
    }
}
