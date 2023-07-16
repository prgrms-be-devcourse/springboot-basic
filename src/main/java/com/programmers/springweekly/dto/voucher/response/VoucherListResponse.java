package com.programmers.springweekly.dto.voucher.response;

import java.util.List;
import lombok.Getter;

@Getter
public class VoucherListResponse {

    private final List<VoucherResponse> voucherList;

    public VoucherListResponse(List<VoucherResponse> voucherList) {
        this.voucherList = voucherList;
    }
    
}
