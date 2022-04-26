package com.dojinyou.devcourse.voucherapplication.voucher.entity;

import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherAmount;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherType;

public abstract class VoucherEntity {
    private final Long id;
    private final VoucherType type;
    private final VoucherAmount amount;

    public VoucherEntity(Long id, VoucherType type, VoucherAmount amount) {
        this.id = id;
        this.type = type;
        this.amount = amount;
    }

    public Long getId() {
        return this.id;
    }

    public VoucherType getType() {
        return this.type;
    }

    public VoucherAmount getAmount() {
        return amount;
    }
}
