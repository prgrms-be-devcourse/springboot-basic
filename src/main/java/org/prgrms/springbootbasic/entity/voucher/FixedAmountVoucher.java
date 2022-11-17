package org.prgrms.springbootbasic.entity.voucher;


import java.util.UUID;

public class FixedAmountVoucher extends Voucher {
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        super(voucherId);
        this.amount = amount;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public long getQuantity() {
        return amount;
    }

    @Override
    public String toString() {
        return "{FixedAmountVoucher - id: %s, amount: %d}".formatted(voucherId, amount);
    }
}
