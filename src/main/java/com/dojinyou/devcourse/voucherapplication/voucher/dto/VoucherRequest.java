package com.dojinyou.devcourse.voucherapplication.voucher.dto;

import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherAmount;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherType;

public class VoucherRequest {
    private final VoucherType type;
    private final VoucherAmount amount;

    public VoucherRequest(VoucherType type, VoucherAmount amount) {
        this.type = type;
        this.amount = amount;
    }

    public VoucherType getVoucherType() {
        return this.type;
    }

    public VoucherAmount getVoucherAmount() {
        return this.amount;
    }
}
