package org.prgrms.devcourse.domain;

import java.io.Serializable;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher, Serializable {
    private final UUID voucherId;
    private final long amount;

    private FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    public static FixedAmountVoucher of(UUID voucherId, long amount) {
        return new FixedAmountVoucher(voucherId, amount);
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher," + voucherId + "," + amount + "\n";
    }
}
