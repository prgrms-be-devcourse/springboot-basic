package org.programers.vouchermanagement.voucher.domain;

import org.programers.vouchermanagement.voucher.exception.WrongVoucherPolicyException;
import org.springframework.context.annotation.Primary;

@Primary
public class PercentDiscountPolicy implements VoucherPolicy {

    private final int percent;

    public PercentDiscountPolicy(int percent) {
        this.percent = percent;
    }

    @Override
    public int discount(int price) {
        validateDiscountPercent(percent);
        return price - price * percent / 100;
    }

    private void validateDiscountPercent(int percent) {
        if (percent < 0 || percent > 100) {
            throw new WrongVoucherPolicyException("기존 금액을 초과해서 할인할 수 없습니다.");
        }
    }

    public int getPercent() {
        return percent;
    }
}
