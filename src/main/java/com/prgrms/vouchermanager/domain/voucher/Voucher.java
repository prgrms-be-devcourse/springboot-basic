package com.prgrms.vouchermanager.domain.voucher;

import lombok.Getter;

import java.util.UUID;


@Getter
public abstract class Voucher {
    protected final UUID id;
    protected final int discount;
    protected VoucherType type;
    protected String date;

    protected Voucher(int discount) {
        this(UUID.randomUUID(), discount);
    }
    protected Voucher(UUID id, int discount) {
        this.id = id;
        this.discount = discount;
    }
    protected Voucher(UUID id, int discount, String date) {
        this.id = id;
        this.discount = discount;
        this.date = date;
    }

    public abstract long discount(long beforeDiscount);
}
