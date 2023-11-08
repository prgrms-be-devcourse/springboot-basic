package com.programmers.vouchermanagement.voucher.domain;

import com.programmers.vouchermanagement.voucher.domain.vouchertype.VoucherType;
import com.programmers.vouchermanagement.voucher.domain.vouchertype.VoucherTypeManager;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Voucher {
    private final UUID id;
    private final LocalDateTime createdAt;
    private VoucherType type;
    private long discountValue;

    public Voucher(UUID id, LocalDateTime createdAt, String typeName, long discountValue) {
        this.id = id;
        this.createdAt = createdAt;
        this.type = VoucherTypeManager.get(typeName);
        type.validateDiscountValue(discountValue);
        this.discountValue = discountValue;
    }

    public Voucher(String typeName, long discountValue) {
        this(UUID.randomUUID(), LocalDateTime.now(), typeName, discountValue);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voucher voucher = (Voucher) o;
        return discountValue == voucher.discountValue && Objects.equals(id, voucher.id) && Objects.equals(createdAt, voucher.createdAt) && Objects.equals(type, voucher.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdAt, type, discountValue);
    }
}
