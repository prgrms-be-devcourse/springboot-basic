package org.programers.vouchermanagement.view;

import org.programers.vouchermanagement.voucher.domain.FixedAmountPolicy;
import org.programers.vouchermanagement.voucher.domain.PercentDiscountPolicy;
import org.programers.vouchermanagement.voucher.domain.VoucherPolicy;

import java.util.Arrays;
import java.util.function.Function;

public enum DiscountPolicyType {
    AMOUNT(1, FixedAmountPolicy::new), PERCENT(2, PercentDiscountPolicy::new);

    private final int number;
    private final Function<Integer, VoucherPolicy> function;

    DiscountPolicyType(int number, Function<Integer, VoucherPolicy> function) {
        this.number = number;
        this.function = function;
    }

    public static DiscountPolicyType from(int number) {
        return Arrays.stream(values())
                .filter(menu -> menu.number == number)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 할인정책 번호입니다. : " + number));
    }

    public VoucherPolicy createPolicy(int value) {
        return function.apply(value);
    }

    public int getNumber() {
        return number;
    }
}
