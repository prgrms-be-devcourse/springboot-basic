package org.programmers.VoucherManagement.voucher.dto;

import java.util.Collections;
import java.util.List;

public class GetVoucherListResponse {
    private final List<GetVoucherResponse> getVoucherListRes;

    public GetVoucherListResponse(List<GetVoucherResponse> getVoucherListRes) {
        this.getVoucherListRes = getVoucherListRes;
    }

    public List<GetVoucherResponse> getGetVoucherListRes() {
        return Collections.unmodifiableList(getVoucherListRes);
    }
}
