package org.programers.vouchermanagement.domain;

import org.programers.vouchermanagement.exception.WrongVoucherPolicyException;
import org.springframework.stereotype.Component;

@Component
public class FixedAmountPolicy implements VoucherPolicy {

    private final int amount = 100;

    @Override
    public int discount(int price) {
        validateDiscountAmount(price);
        return price - amount;
    }

    private void validateDiscountAmount(int price) {
        if (price - amount < 0) {
            throw new WrongVoucherPolicyException("기존 금액을 초과해서 할인할 수 없습니다.");
        }
    }
}
