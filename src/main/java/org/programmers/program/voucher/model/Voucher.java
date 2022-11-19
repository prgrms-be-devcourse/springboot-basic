package org.programmers.program.voucher.model;

import java.util.UUID;

public abstract class Voucher {
    protected final UUID id;
    protected final Long discountAmount;

    protected Voucher(UUID id, Long discountAmount) {
        this.id = id;
        this.discountAmount = discountAmount;
    }

    public UUID getVoucherId(){
        return this.id;
    }

    public Long getDiscountAmount(){
        return this.discountAmount;
    }

    abstract Long discount(Long price);
}
