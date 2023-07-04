package org.programers.vouchermanagement.voucher.domain;

import org.programers.vouchermanagement.voucher.exception.WrongVoucherPolicyException;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
public class PercentDiscountPolicy implements VoucherPolicy {

    private final int percent;

    public PercentDiscountPolicy(int percent) {
        validateDiscountPercent(percent);
        this.percent = percent;
    }

    @Override
    public int discount(int price) {
        return price - price * percent / 100;
    }

    @Override
    public int getValue() {
        return percent;
    }

    private void validateDiscountPercent(int percent) {
        if (percent < 0 || percent > 100) {
            throw new WrongVoucherPolicyException("가능한 할인 비율은 0 이상 100 이하 입니다.");
        }
    }
}
