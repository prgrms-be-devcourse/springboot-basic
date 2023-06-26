package org.prgrms.application.domain;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher<Long> {
    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        if (amount <= 0) throw new IllegalArgumentException("금액은 양수여야 합니다.");
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public Long discount(Long beforeDiscount) {
        long discountedAmount = beforeDiscount - amount;
        return (discountedAmount < 0) ? 0 : discountedAmount;
    }


    @Override
    public String toString() {
        return "Fixed { " + "voucherId=" + voucherId + ", amount=" + amount + '}';
    }
}
