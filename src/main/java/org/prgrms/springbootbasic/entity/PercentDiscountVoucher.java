package org.prgrms.springbootbasic.entity;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    public static final String PERCENT_MIN_RANGE_EXP_MSG = "percent는 0보다 커야합니다.";
    public static final String PERCENT_MAX_RANGE_EXP_MSG = "percnet는 100보다 작거나 같아야합니다.";
    public static final int MIN_RANGE = 0;
    public static final int MAX_RANGE = 100;

    private final UUID voucherId;
    private final int percent;

    public PercentDiscountVoucher(UUID voucherId, int percent) {
        validatePercentRange(percent);

        this.voucherId = voucherId;
        this.percent = percent;
    }

    private void validatePercentRange(int percent) {
        if (percent <= MIN_RANGE) {
            throw new IllegalArgumentException(PERCENT_MIN_RANGE_EXP_MSG);
        }
        if (percent > MAX_RANGE) {
            throw new IllegalArgumentException(PERCENT_MAX_RANGE_EXP_MSG);
        }
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    public int getPercent() {
        return percent;
    }
}
