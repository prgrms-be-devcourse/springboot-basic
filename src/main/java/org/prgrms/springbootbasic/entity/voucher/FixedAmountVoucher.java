package org.prgrms.springbootbasic.entity.voucher;


public class FixedAmountVoucher extends Voucher {
    private final long amount;

    public FixedAmountVoucher(long amount) {
        this.amount = amount;
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
