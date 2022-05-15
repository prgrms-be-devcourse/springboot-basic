package com.dojinyou.devcourse.voucherapplication.voucher.dto;

import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherAmount;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherType;

public class VoucherCreateRequest {
    private final VoucherType type;
    private final VoucherAmount amount;


    public VoucherCreateRequest(String type, int amount) {
        this.type = VoucherType.from(type);
        this.amount = VoucherAmount.of(this.type, amount);
    }

    public VoucherType getType() {
        return type;
    }

    public VoucherAmount getAmount() {
        return amount;
    }
}
