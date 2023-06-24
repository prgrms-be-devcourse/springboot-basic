package org.programers.vouchermanagement.voucher.domain;

import org.programers.vouchermanagement.voucher.exception.WrongVoucherPolicyException;

public class FixedAmountPolicy implements VoucherPolicy {

    private final int amount;

    public FixedAmountPolicy(int amount) {
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

    public int getAmount() {
        return amount;
    }
}
