package com.programmers.vouchermanagement.domain.voucher;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
public abstract class Voucher {
    private UUID id;
    protected VoucherType type;
    protected long amount;
    protected LocalDateTime createdAt;

    public Voucher(UUID id, VoucherType type, long amount, LocalDateTime createdAt) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public Voucher(VoucherType type, long amount) {
        this.type = type;
        this.amount = amount;
    }

    public Voucher(UUID id, VoucherType type, long amount) {
        this.id = id;
        this.type = type;
        this.amount = amount;
    }

    public Voucher(UUID id, long amount, LocalDateTime createdAt) {
        this.id = id;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public Voucher(VoucherType type, long amount, LocalDateTime createdAt) {
        this.type = type;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public static Voucher from(UUID id, VoucherType type, long amount, LocalDateTime createdAt) {
        return VoucherFactory.create(id, type, amount, createdAt);
    }
}
