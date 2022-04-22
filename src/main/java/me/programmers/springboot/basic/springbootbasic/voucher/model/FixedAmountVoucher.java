package me.programmers.springboot.basic.springbootbasic.voucher.model;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        super(voucherId);
        validate(amount);
        this.amount = amount;
    }

    private void validate(long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("음수 값으로 할인 할 수 없습니다.");
        }
        if (amount == 0) {
            throw new IllegalArgumentException("0원으로 할인 할 수 없습니다.");
        }
    }

    public long getAmount() {
        return amount;
    }

    @Override
    public long discount(long beforeDiscount) {
        var discountedAmount = beforeDiscount - amount;
        return discountedAmount < 0 ? 0 : discountedAmount;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "voucherId=" + super.getVoucherId() +
                ", amount=" + amount +
                '}';
    }
}
