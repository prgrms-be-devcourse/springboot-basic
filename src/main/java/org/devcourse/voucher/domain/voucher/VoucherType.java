package org.devcourse.voucher.domain.voucher;

import org.devcourse.voucher.domain.voucher.policy.DiscountPolicy;
import org.devcourse.voucher.domain.voucher.policy.FixedDiscountPolicy;
import org.devcourse.voucher.domain.voucher.policy.PercentDiscountPolicy;

import java.util.Arrays;
import java.util.function.Function;

public enum VoucherType {
    FIX("fix", FixedDiscountPolicy::new),
    PERCENT("percent", PercentDiscountPolicy::new);

    private final String typeName;
    private final Function<Integer, DiscountPolicy> policy;

    VoucherType(String typeName, Function<Integer, DiscountPolicy> policy) {
        this.typeName = typeName;
        this.policy = policy;
    }

    public static VoucherType find(String typeName) {
        return Arrays.stream(values())
                .filter(type -> type.typeName.equals(typeName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("사용 불가능한 바우처 타입입니다"));
    }

    public DiscountPolicy createPolicy(int amount) {
        return policy.apply(amount);
    }

}
