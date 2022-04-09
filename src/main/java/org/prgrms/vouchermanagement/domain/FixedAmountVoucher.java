package org.prgrms.vouchermanagement.domain;

import java.io.Serializable;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher, Serializable {

    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {

        return voucherId;
    }

    @Override
    public String toString() {

        return voucherId + " " + amount + " FixedAmountVoucher";
    }
}
