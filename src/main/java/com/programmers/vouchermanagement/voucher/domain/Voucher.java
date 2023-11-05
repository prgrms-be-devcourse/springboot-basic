package com.programmers.vouchermanagement.voucher.domain;

import com.programmers.vouchermanagement.voucher.domain.vouchertype.VoucherType;
import com.programmers.vouchermanagement.voucher.domain.vouchertype.VoucherTypeManager;

import java.time.LocalDateTime;
import java.util.UUID;

public class Voucher {
    private final UUID id;
    private final LocalDateTime createdAt;
    private VoucherType type;
    private long discountValue;

    public Voucher(String typeName, long discountValue) {
        id = UUID.randomUUID();
        createdAt = LocalDateTime.now();

        this.type = VoucherTypeManager.get(typeName);
        type.validateDiscountValue(discountValue);
        this.discountValue = discountValue;
    }

    public Voucher(UUID id, LocalDateTime createdAt, String typeName, long discountValue) {
        this.id = id;
        this.createdAt = createdAt;
        this.type = VoucherTypeManager.get(typeName);
        type.validateDiscountValue(discountValue);
        this.discountValue = discountValue;
    }

    public String getTypeName() {
        return type.getName();
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public long getDiscountValue() {
        return discountValue;
    }
}
