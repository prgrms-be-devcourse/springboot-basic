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

    @Override
    public boolean equals(Object other) {
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }

        if (this == other) {
            return true;
        }

        VoucherEntity otherEntity = (VoucherEntity) other;
        if (this.id.equals(otherEntity.getId())) {
            return true;
        }
        return false;
    }

    public Long getId() {
        return this.id;
    }

    public VoucherType getVoucherType() {
        return this.type;
    }

    public VoucherAmount getVoucherAmount() {
        return amount;
    }

    public int getAmount() {
        return amount.getAmount();
    }
}
