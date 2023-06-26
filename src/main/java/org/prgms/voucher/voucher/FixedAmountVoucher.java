package org.prgms.voucher.voucher;

import lombok.ToString;

import java.util.UUID;

@ToString
public class FixedAmountVoucher implements Voucher {

    private final long amount;
    private final UUID id;

    public FixedAmountVoucher(long amount, UUID id) {
        this.amount = amount;
        this.id = id;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public long discount(long price) {
        return Math.max(0, price - amount);
    }
}
