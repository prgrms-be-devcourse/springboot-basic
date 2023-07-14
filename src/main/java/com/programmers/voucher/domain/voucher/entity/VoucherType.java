package com.programmers.voucher.domain.voucher.entity;

import com.programmers.voucher.exception.InvalidCommandException;
import lombok.Getter;

import java.util.Arrays;
import java.util.function.BiFunction;

import static com.programmers.voucher.constant.ErrorCode.INVALID_COMMAND;

@Getter
public enum VoucherType {
    FIXED(1, "금액 할인", (price, amount) -> Math.max(0L, price - amount)),
    PERCENT(2, "퍼센트 할인", (price, percent) -> Math.max(0L, price * (percent / 100)));

    private final int number;
    private final String text;
    private final BiFunction<Long, Integer, Long> discount;

    VoucherType(int number, String text, BiFunction<Long, Integer, Long> discount) {
        this.number = number;
        this.text = text;
        this.discount = discount;
    }

    public static VoucherType findByNumber(int number) {
        return Arrays.stream(VoucherType.values())
                .filter(voucher -> voucher.isEqualTo(number))
                .findFirst()
                .orElseThrow(() -> new InvalidCommandException(INVALID_COMMAND));
    }

    public long discount(long price, int amount) {
        return discount.apply(price, amount);
    }

    private boolean isEqualTo(int number) {
        return this.number == number;
    }

    @Override
    public String toString() {
        return number + ". " + text;
    }
}
