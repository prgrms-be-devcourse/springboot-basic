package org.prgrms.springbootbasic.voucher;


import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId = UUID.randomUUID();
    private final long amount;

    public FixedAmountVoucher(long amount) {
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public String toString() {
        return "{FixedAmountVoucher - id: %s, amount: %d}".formatted(voucherId, amount);
    }
}
