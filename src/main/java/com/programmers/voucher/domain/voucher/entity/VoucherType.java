package com.programmers.voucher.domain.voucher.entity;

import lombok.Getter;

import java.util.function.BiFunction;

@Getter
public enum VoucherType {
    FIXED("금액 할인", (price, amount) -> Math.max(0L, price - amount)),
    PERCENT("퍼센트 할인", (price, percent) -> Math.max(0L, price * (percent / 100)));

    private final String text;
    private final BiFunction<Long, Integer, Long> discount;

    VoucherType(String text, BiFunction<Long, Integer, Long> discount) {
        this.text = text;
        this.discount = discount;
    }

    public long discount(long price, int amount) {
        return discount.apply(price, amount);
    }
}
