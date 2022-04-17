package org.prgrms.springbootbasic.entity;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

    private final int amount;

    public FixedAmountVoucher(UUID voucherId, int amount) {
        super(voucherId);
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
