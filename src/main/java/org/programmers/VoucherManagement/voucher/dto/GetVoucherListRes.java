package org.programmers.VoucherManagement.voucher.dto;

import java.util.Collections;
import java.util.List;

public class GetVoucherListRes {
    private final List<GetVoucherRes> getVoucherListRes;

    public GetVoucherListRes(List<GetVoucherRes> getVoucherListRes) {
        this.getVoucherListRes = getVoucherListRes;
    }

    public List<GetVoucherRes> getGetVoucherListRes() {
        return Collections.unmodifiableList(getVoucherListRes);
    }
}
