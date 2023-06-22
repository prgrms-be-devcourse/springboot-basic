package org.devcourse.voucher.domain.voucher;

public record Money(int amount) {

    private static final Money ZERO_AMOUNT = new Money(0);

    public static Money of(int remainMoneyAmount) {
        if (remainMoneyAmount < 0) {
            return ZERO_AMOUNT;
        }

        return new Money(remainMoneyAmount);
    }
}
