package org.prgrms.springbootbasic.entity;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    public static final String AMOUNT_MIN_RANGE_EXP_MSG = "amount는 0보다 작거나 같을 수 없습니다.";
    public static final String AMOUNT_MAX_RANGE_EXP_MSG = "amount는 100000보다 작아야 합니다.";
    public static final long MIN_RANGE = 0L;
    public static final long MAX_RANGE = 100000L;

    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        validateAmountRange(amount);

        this.voucherId = voucherId;
        this.amount = amount;
    }

    private void validateAmountRange(long amount) {
        if (amount <= MIN_RANGE) {
            throw new IllegalArgumentException(AMOUNT_MIN_RANGE_EXP_MSG);
        }
        if (amount >= MAX_RANGE) {
            throw new IllegalArgumentException(AMOUNT_MAX_RANGE_EXP_MSG);
        }
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    public long getAmount() {
        return amount;
    }
}
