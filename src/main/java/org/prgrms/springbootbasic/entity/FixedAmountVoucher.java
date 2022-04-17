package org.prgrms.springbootbasic.entity;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        super(voucherId);
        this.amount = amount;
    }

    public long getAmount() {
        return amount;
    }
}
