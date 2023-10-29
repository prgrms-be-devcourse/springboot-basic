package com.programmers.vouchermanagement.domain.voucher;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@EqualsAndHashCode
public abstract class Voucher {
    private UUID id;
    protected final VoucherType type;
    protected long amount;

    public Voucher(UUID id, VoucherType type, long amount) {
        this.id = id;
        this.type = type;
        this.amount = amount;
    }

    public Voucher(VoucherType type, long amount) {
        this.type = type;
        this.amount = amount;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Voucher{id=" + id + ", amount=" + amount + ", type=" + type + '}';
    }
}
