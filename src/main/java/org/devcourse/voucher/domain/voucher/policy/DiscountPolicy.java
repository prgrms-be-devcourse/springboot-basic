package org.devcourse.voucher.domain.voucher.policy;

public abstract class DiscountPolicy {

    protected final int providedAmount;

    protected DiscountPolicy(int providedAmount) {
        validateProvidedAmount(providedAmount);
        this.providedAmount = providedAmount;
    }

    private void validateProvidedAmount(int providedAmount) {
        if (invalid(providedAmount)) {
            throw new RuntimeException("입력 범위를 벗어났습니다");
        }
    }

    public abstract int discount(int targetAmount);

    protected abstract boolean invalid(int providedAmount);
}
