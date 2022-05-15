package com.dojinyou.devcourse.voucherapplication.voucher.entity;

import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherAmount;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherType;

import java.time.LocalDateTime;

public abstract class VoucherEntity {
    private final Long id;
    private final VoucherType type;
    private final VoucherAmount amount;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static final String ERROR_MESSAGE_FOR_NULL = "잘못된 입력입니다.";


    protected VoucherEntity(Long id, VoucherType type, VoucherAmount amount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static VoucherEntity of(Long id, VoucherType type, VoucherAmount amount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        switch (type) {
            case FIXED:
                return new FixedAmountVoucherEntity(id, type, amount, createdAt, updatedAt);
            case PERCENT:
                return new PercentAmountVoucherEntity(id, type, amount,createdAt, updatedAt);
        }
        throw new IllegalArgumentException(ERROR_MESSAGE_FOR_NULL);
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
        return amount.getValue();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
