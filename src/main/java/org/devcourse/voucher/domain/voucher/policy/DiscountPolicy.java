package org.devcourse.voucher.domain.voucher.policy;

import org.devcourse.voucher.domain.voucher.VoucherType;

public abstract class DiscountPolicy {

    protected final int providedAmount;

    protected DiscountPolicy(int providedAmount) {
        validateProvidedAmount(providedAmount);
        this.providedAmount = providedAmount;
    }

    public static DiscountPolicy of(VoucherType type, int providedAmount) {
        validateType(type);
        return switch (type) {
            case PERCENT -> new PercentDiscountPolicy(providedAmount);
            case FIX -> new FixedDiscountPolicy(providedAmount);
        };
    }

    private static void validateType(VoucherType type) {
        if (type == null) {
            throw new RuntimeException("바우처 타입은 빈 값일 수 없습니다");
        }
    }

    private void validateProvidedAmount(int providedAmount) {
        if (invalid(providedAmount)) {
            throw new RuntimeException();
        }
    }

    public abstract int discount(int targetAmount);

    protected abstract boolean invalid(int providedAmount);

}
