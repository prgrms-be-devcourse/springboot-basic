package org.prgrms.springbootbasic.entity;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    public static final String AMOUNT_MIN_RANGE_EXP_MSG = "amount는 0보다 작거나 같을 수 없습니다.";
    public static final String AMOUNT_MAX_RANGE_EXP_MSG = "amount는 100000보다 작아야 합니다.";
    public static final int MIN_RANGE = 0;
    public static final int MAX_RANGE = 100000;

    private final UUID voucherId;
    private final int amount;

    public FixedAmountVoucher(UUID voucherId, int amount) {
        validateAmountRange(amount);

        this.voucherId = voucherId;
        this.amount = amount;
    }

    private void validateAmountRange(int amount) {
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

    public int getAmount() {
        return amount;
    }
}
