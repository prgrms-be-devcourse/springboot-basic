package org.prgrms.vouchermanagement.voucher.policy;

import org.prgrms.vouchermanagement.exception.InvalidRangeException;

public class PercentDiscountVoucher implements DiscountPolicy {

    private final long percent;
    private final PolicyStatus policyStatus = PolicyStatus.PERCENT;

    public PercentDiscountVoucher(long percent) {
        this.percent = percent;

        if (percent < 0 || percent > 100) {
            throw new InvalidRangeException("PercentDiscountPolicy는 0~100 사이의 값을 가져야 합니다.");
        }
    }

    @Override
    public long getAmountOrPercent() {
        return percent;
    }

    @Override
    public PolicyStatus getPolicyStatus() {
        return policyStatus;
    }

    @Override
    public long discount(long price) {
        return (price - (price/percent));
    }
}
