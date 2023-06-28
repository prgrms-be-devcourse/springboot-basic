package org.devcourse.voucher.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Voucher {
    private final UUID voucherId;
    Voucher() {
        this.voucherId = UUID.randomUUID();
    }

    public UUID getVoucherId(){
        return voucherId;
    }

    public abstract String getType();
    public abstract long discount(long beforeDiscount) throws IllegalArgumentException;
}
