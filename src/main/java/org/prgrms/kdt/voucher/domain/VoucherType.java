package org.prgrms.kdt.voucher.domain;

import org.prgrms.kdt.voucher.exception.InvalidDiscountException;

import java.util.Arrays;
import java.util.function.Function;

public enum VoucherType {
    FIXED(1, "FixedAmountVoucher", FixedDiscountPolicy::new),
    PERCENT(2, "PercentDiscountVoucher", PercentDiscountPolicy::new);

    private final int number;
    private final String name;

    private final Function<Double, DiscountPolicy> function;

    VoucherType(int number, String name, Function<Double, DiscountPolicy> function) {
        this.number = number;
        this.name = name;
        this.function = function;
    }

    public static VoucherType getType(String str) {
        int curNumber = Integer.parseInt(str);
        return Arrays.stream(VoucherType.values())
                .filter((e) -> e.number == curNumber)
                .findFirst()
                .orElseThrow(() -> new InvalidDiscountException("입력한 바우처 타입은 지원하지 않습니다."));
    }

    public String getName() {
        return name;
    }

    public DiscountPolicy createPolicy(double amount) {
        return function.apply(amount);
    }
}
