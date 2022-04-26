package org.prgrms.springbootbasic.entity.voucher;

import static org.prgrms.springbootbasic.exception.ServiceExceptionMessage.PERCENT_MAX_RANGE_EXP_MSG;
import static org.prgrms.springbootbasic.exception.ServiceExceptionMessage.PERCENT_MIN_RANGE_EXP_MSG;

import java.util.UUID;
import org.prgrms.springbootbasic.exception.PercentRangeMaxException;
import org.prgrms.springbootbasic.exception.PercentRangeMinException;

public class PercentDiscountVoucher extends Voucher {

    private static final int MIN_RANGE = 0;
    private static final int MAX_RANGE = 100;

    private final int percent;

    public PercentDiscountVoucher(UUID voucherId, int percent) {
        super(voucherId);

        validatePercentRange(percent);
        this.percent = percent;
    }

    public PercentDiscountVoucher(UUID voucherId, UUID customerId, int percent) {
        super(voucherId, customerId);

        validatePercentRange(percent);
        this.percent = percent;
    }

    private void validatePercentRange(int percent) {
        if (percent <= MIN_RANGE) {
            throw new PercentRangeMinException(PERCENT_MIN_RANGE_EXP_MSG);
        }
        if (percent > MAX_RANGE) {
            throw new PercentRangeMaxException(PERCENT_MAX_RANGE_EXP_MSG);
        }
    }

    @Override
    public boolean isFixed() {
        return false;
    }

    @Override
    public boolean isPercent() {
        return true;
    }

    public int getPercent() {
        return percent;
    }
}
