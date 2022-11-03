package org.programmers.springbootbasic.domain;

import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final long amount;

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getAmount() {
        return amount;
    }
}
