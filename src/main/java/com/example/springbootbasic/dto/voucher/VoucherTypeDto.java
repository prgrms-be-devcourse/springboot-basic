package com.example.springbootbasic.dto.voucher;

import com.example.springbootbasic.domain.voucher.VoucherType;

public class VoucherTypeDto {

    private final VoucherType voucherType;

    public VoucherTypeDto(VoucherType voucherType) {
        this.voucherType = voucherType;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }
}
