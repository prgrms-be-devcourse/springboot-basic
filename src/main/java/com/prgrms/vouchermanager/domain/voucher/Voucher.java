package com.prgrms.vouchermanager.domain.voucher;

import lombok.Getter;

import java.util.UUID;


@Getter
public abstract class Voucher {
    protected final UUID id;
    protected final long discount;

    public Voucher(long discount) {
        this.id = UUID.randomUUID();
        this.discount = discount;
    }
    public Voucher(UUID id, long discount) {
        this.id = id;
        this.discount = discount;
    }

    public abstract long discount(long beforeDiscount);
    public abstract String toString();

    ;

}
