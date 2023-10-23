package com.programmers.springbootbasic.domain.voucher.service;

import com.programmers.springbootbasic.domain.voucher.entity.FixedAmountVoucher;
import com.programmers.springbootbasic.domain.voucher.entity.PercentDiscountVoucher;
import com.programmers.springbootbasic.domain.voucher.entity.Voucher;
import com.programmers.springbootbasic.domain.voucher.exception.ErrorMsg;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public enum VoucherType {
    FIXED_AMOUNT(1, (UUID voucherId, Long value) -> {
        if (value < 0) {
            throw new IllegalArgumentException(ErrorMsg.WRONG_FIXED_AMOUNT_VALUE_INPUT.getMessage());
        }
        return new FixedAmountVoucher(voucherId, value);
    }, FixedAmountVoucher.class::isInstance),
    PERCENT_DISCOUNT(2, (UUID voucherId, Long value) -> {
        if (value < 0 || value > 100) {
            throw new IllegalArgumentException(ErrorMsg.WRONG_PERCENT_DISCOUNT_VALUE_INPUT.getMessage());
        }
        return new PercentDiscountVoucher(voucherId, value);
    }, PercentDiscountVoucher.class::isInstance),
    ERROR(99, (UUID voucherId, Long value) -> {
        throw new IllegalArgumentException(ErrorMsg.WRONG_VOUCHER_TYPE_NUMBER.getMessage());
    }, Objects::nonNull);

    private final int number;
    private final BiFunction<UUID, Long, Voucher> biFunction;
    private final Predicate<Voucher> predicate;

    VoucherType(int number, BiFunction<UUID, Long, Voucher> biFunction, Predicate<Voucher> predicate) {
        this.number = number;
        this.biFunction = biFunction;
        this.predicate = predicate;
    }

    private static VoucherType findByNumber(int number) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.number == number)
                .findAny()
                .orElse(ERROR);
    }

    public static Voucher of(int number, UUID voucherId, long value) {
        return findByNumber(number).biFunction.apply(voucherId, value);
    }

    public static int predictVoucherType(Voucher voucher) {
        return Arrays.stream(VoucherType.values())
                .filter(v -> v.predicate.test(voucher))
                .findAny()
                .orElse(ERROR).number;
    }
}
