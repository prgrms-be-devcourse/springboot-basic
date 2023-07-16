package com.programmers.springweekly.dto.voucher.request;

import com.programmers.springweekly.domain.voucher.VoucherType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class VoucherCreateRequest {

    private long discountAmount;
    private VoucherType voucherType;

    @Builder
    public VoucherCreateRequest(long discountAmount, VoucherType voucherType) {
        this.discountAmount = discountAmount;
        this.voucherType = voucherType;
    }
    
}
