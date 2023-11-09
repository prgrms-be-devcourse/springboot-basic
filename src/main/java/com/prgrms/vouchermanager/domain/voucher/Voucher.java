package com.prgrms.vouchermanager.domain.voucher;

import lombok.Getter;

import java.util.UUID;


@Getter
public abstract class Voucher {
    protected final UUID id;
    protected final int discount;
    protected VoucherType type;

    protected Voucher(int discount) {
        this(UUID.randomUUID(), discount);
    }

    protected Voucher(UUID id, int discount) {
        this.id = id;
        this.discount = discount;
    }

    public abstract long discount(long beforeDiscount);
}
