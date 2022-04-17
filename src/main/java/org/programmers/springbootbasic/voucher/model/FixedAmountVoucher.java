package org.programmers.springbootbasic.voucher.model;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher{
    private static final long MAX_VOUCHER_AMOUNT = 10000;
    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        if (amount < 0) throw new IllegalArgumentException("할인 금액은 0보다 커야 합니다.");
        if (amount == 0) throw new IllegalArgumentException("할인 금액은 0이 아니어야 합니다.");
        if (amount > MAX_VOUCHER_AMOUNT) throw new IllegalArgumentException("할인금액은 %d보다 작아야 합니다.".formatted(MAX_VOUCHER_AMOUNT));
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher" +
                " voucherId: " + voucherId +
                " amount: " + amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    public long discount(long beforeDiscount) {
        var discountedAmount = beforeDiscount - amount;
        return (discountedAmount < 0) ? 0 : discountedAmount;
    }
}
