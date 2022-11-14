package org.prgrms.kdt.voucher;

import org.prgrms.kdt.exceptions.AmountException;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private static final long MAX_VOUCHER_LIMIT = 10000;
    private static final long MIN_VOUCHER_LIMIT = 10;
    private final UUID voucherId;
    private final double amount;

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    public FixedAmountVoucher(UUID voucherId, double amount) {
        if (amount <= MIN_VOUCHER_LIMIT) {
            throw new AmountException("숫자가 %d보다 커야 합니다. 다시 작성해주세요.".formatted(MIN_VOUCHER_LIMIT));
        }
        if (amount > MAX_VOUCHER_LIMIT) {
            throw new AmountException("%d 이하여야 합니다.".formatted(MAX_VOUCHER_LIMIT));
        }
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public double discount(double beforeDiscount) {
        var discountedAmount = beforeDiscount - amount;
        return Double.max(discountedAmount, 0);
    }
}
