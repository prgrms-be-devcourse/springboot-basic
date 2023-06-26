package org.devcourse.voucher.domain.voucher;

public class Money {
    public static final Money ZERO_AMOUNT = new Money(0);

    private final int amount;

    private Money(int amount) {
        this.amount = amount;
    }

    public static Money of(int amount) {
        if (amount < 0) {
            throw new RuntimeException("금액이 0 미만일 수 없습니다");
        }

        return new Money(amount);
    }

    public Money minus(Money discountAmount) {
        int calculatedResultAmount = this.amount - discountAmount.amount;
        if (calculatedResultAmount < 0) {
            return ZERO_AMOUNT;
        }

        return new Money(calculatedResultAmount);
    }

    public Money percent(int percentAmount) {
        int calculatedResultAmount = this.amount * percentAmount / 100;

        return Money.of(calculatedResultAmount);
    }
}
