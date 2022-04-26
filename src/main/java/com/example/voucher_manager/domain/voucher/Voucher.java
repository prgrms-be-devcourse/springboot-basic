package com.example.voucher_manager.domain.voucher;

import java.io.Serializable;
import java.util.UUID;

public abstract class Voucher implements Serializable {

    protected final UUID voucherId;
    protected UUID ownerId;

    protected Voucher(UUID voucherId, UUID ownerId) {
        this.voucherId = voucherId;
        this.ownerId = ownerId;
    }

    protected Voucher(UUID voucherId) {
        this(voucherId, null);
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void provideToCustomer(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public abstract Long discount(Long regularPrice);
    public abstract VoucherType getVoucherType();
    public abstract Long getDiscountInformation();
}
