package com.programmers.vouchermanagement.domain.voucher;

import java.util.UUID;

public abstract class Voucher {
    protected final UUID id;
    protected final String name;
    protected final float discountAmount;

    protected Voucher(UUID id, String name, float discountAmount) {
        this.id = id;
        this.name = name;
        this.discountAmount = discountAmount;
    }

    public abstract UUID getId();

    public abstract float discount(float beforeDiscount);

    public abstract void validateDiscountAmount(float discountAmount);

    public abstract String joinInfo(String separator);
}
