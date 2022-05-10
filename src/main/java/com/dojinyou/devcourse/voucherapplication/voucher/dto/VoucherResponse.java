package com.dojinyou.devcourse.voucherapplication.voucher.dto;

import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherAmount;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherType;

import java.time.LocalDateTime;

public class VoucherResponse {
    private final Long id;
    private final VoucherType type;
    private final VoucherAmount amount;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public VoucherResponse(Long id, VoucherType type, VoucherAmount amount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return System.lineSeparator() +
                "Voucher ID : " + id + System.lineSeparator() +
                "Voucher Type : " + type + System.lineSeparator() +
                "Voucher Amount : " + amount.getValue() + System.lineSeparator() +
                "Created At : " + createdAt + System.lineSeparator() +
                "Updated At : " + updatedAt + System.lineSeparator();
    }

    public Long getId() {
        return this.id;
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
