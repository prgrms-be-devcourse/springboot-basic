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
        if (other == null || !(other instanceof VoucherEntity)) {
            return false;
        }

        if (this == other) {
            return true;
        }

        VoucherEntity otherEntity = (VoucherEntity) other;
        if (this.id != otherEntity.getId()) {
            return false;
        }
        if (this.type != otherEntity.getVoucherType()) {
            return false;
        }
        if (this.getVoucherAmount() != otherEntity.getVoucherAmount() || this.getAmount() != otherEntity.getAmount()) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;

        result = (result * PRIME) + this.id.hashCode();
        result = (result * PRIME) + this.type.hashCode();
        result = (result * PRIME) + this.getVoucherAmount().hashCode();
        result = (result * PRIME) + this.getAmount();

        return result;
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
