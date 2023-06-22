package org.devcourse.voucher.domain.voucher;

public record Money(int amount) {

    public static final Money ZERO_AMOUNT = new Money(0);

    public static Money of(int amount) {
        if (amount >= 0) {
            return new Money(amount);
        }
        throw new RuntimeException("금액이 0 미만일 수 없습니다");
    }
}
