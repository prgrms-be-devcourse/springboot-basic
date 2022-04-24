package com.dojinyou.devcourse.voucherapplication.voucher.dto;

import com.dojinyou.devcourse.voucherapplication.voucher.entity.VoucherAmount;
import com.dojinyou.devcourse.voucherapplication.voucher.entity.VoucherType;

public class VoucherRequestDto {
    private final VoucherType voucherType;
    private final VoucherAmount voucherAmount;

    public VoucherRequestDto(VoucherType voucherType, VoucherAmount voucherAmount) {
        this.voucherType = voucherType;
        this.voucherAmount = voucherAmount;
    }
}
