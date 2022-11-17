package org.programmers.spbw1.voucher;

import java.util.UUID;

public abstract class Voucher {
    protected UUID id;
    protected Long amount;

    public Voucher(UUID id, Long amount) {
        this.id = id;
        this.amount = amount;
    }

    public UUID getVoucherID() {return null;};
    public long discount(long beforeDiscount) {return 0L;};
}
