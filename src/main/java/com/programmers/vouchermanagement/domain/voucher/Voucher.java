package com.programmers.vouchermanagement.domain.voucher;

import lombok.Getter;

import java.util.UUID;

@Getter
public abstract class Voucher {
    private UUID id;
    protected final long amount;

    public Voucher(long amount) {
        this.amount = amount;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public abstract VoucherType getType();

    @Override
    public String toString() {
        return "Voucher{id=" + id + ", amount=" + amount + ", type=" + getType() + '}';
    }
}
