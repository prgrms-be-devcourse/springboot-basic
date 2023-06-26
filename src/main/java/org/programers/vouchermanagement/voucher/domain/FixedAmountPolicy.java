package org.programers.vouchermanagement.voucher.domain;

import org.programers.vouchermanagement.voucher.exception.WrongVoucherPolicyException;

public class FixedAmountPolicy implements VoucherPolicy {

    private final int amount;

    public FixedAmountPolicy(int amount) {
        validatePositiveAmount(amount);
        this.amount = amount;
    }

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

    private void validatePositiveAmount(int amount) {
        if (amount < 0) {
            throw new WrongVoucherPolicyException("가능한 할인 금액은 0 이상 입니다.");
        }
    }

    public int getAmount() {
        return amount;
    }
}
