package com.programmers.vouchermanagement.domain.voucher;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
public abstract class Voucher {
    private UUID id;
    protected VoucherType type;
    protected long amount;

    private Voucher() {}

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
}
