package com.dev.bootbasic.voucher.domain;

import java.util.UUID;

public abstract class AbstractVoucher implements Voucher{

    protected final UUID id;
    protected final int discountAmount;

    protected AbstractVoucher(UUID id, int discountAmount) {
        this.id = id;
        this.discountAmount = discountAmount;
    }

    public UUID getId() {
        return id;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

}
