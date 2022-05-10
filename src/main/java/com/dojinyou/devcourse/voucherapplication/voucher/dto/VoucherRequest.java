package com.dojinyou.devcourse.voucherapplication.voucher.dto;

import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherAmount;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherType;

import java.time.LocalDateTime;

public class VoucherRequest {
    private final Long id;
    private final VoucherType type;
    private final VoucherAmount amount;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public VoucherRequest(VoucherType type, VoucherAmount amount) {
        this(null, type, amount, null, null);
    }

    public VoucherRequest(Long id, VoucherType type, VoucherAmount amount,
                          LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public VoucherType getType() {
        return this.type;
    }

    public VoucherAmount getAmount() {
        return this.amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
