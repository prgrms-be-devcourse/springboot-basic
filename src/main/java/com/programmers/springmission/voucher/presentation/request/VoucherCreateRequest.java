package com.programmers.springmission.voucher.presentation.request;

import com.programmers.springmission.voucher.domain.enums.VoucherType;
import lombok.Getter;

@Getter
public class VoucherCreateRequest {

    private final VoucherType voucherType;
    private final long amount;

    public VoucherCreateRequest(VoucherType voucherType, long amount) {
        this.voucherType = voucherType;
        this.amount = amount;
    }
}

