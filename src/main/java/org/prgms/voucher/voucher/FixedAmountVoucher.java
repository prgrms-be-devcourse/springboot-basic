package org.prgms.voucher.voucher;

import lombok.ToString;

import java.util.UUID;

@ToString
public class FixedAmountVoucher extends Voucher {

    private final long amount;

    public FixedAmountVoucher(long amount, UUID id) {
        super(id);
        this.amount = amount;
    }

    @Override
    public long discount(long price) {
        return Math.max(0, price - amount);
    }
}
