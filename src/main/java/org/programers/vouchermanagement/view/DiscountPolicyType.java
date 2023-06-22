package org.programers.vouchermanagement.view;

import java.util.Arrays;

public enum DiscountPolicyType {
    AMOUNT(1), PERCENT(2);

    private final int number;

    DiscountPolicyType(int number) {
        this.number = number;
    }

    public static DiscountPolicyType from(int number) {
        return Arrays.stream(values())
                .filter(menu -> menu.number == number)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 할인정책 번호입니다. : " + number));
    }

    public boolean isAmount() {
        return this == DiscountPolicyType.AMOUNT;
    }

    public boolean isPercent() {
        return this == DiscountPolicyType.PERCENT;
    }
}
