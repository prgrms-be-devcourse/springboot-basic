package com.programmers.springbootbasic.domain.voucher.service;

import com.programmers.springbootbasic.common.utils.TriFunction;
import com.programmers.springbootbasic.domain.voucher.entity.FixedAmountVoucher;
import com.programmers.springbootbasic.domain.voucher.entity.PercentDiscountVoucher;
import com.programmers.springbootbasic.domain.voucher.entity.Voucher;
import com.programmers.springbootbasic.domain.voucher.exception.ErrorMsg;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;

public enum VoucherType {
    FIXED_AMOUNT(1, (UUID voucherId, Long value, LocalDate localDate) -> {
        if (value < 0) {
            throw new IllegalArgumentException(ErrorMsg.WRONG_FIXED_AMOUNT_VALUE_INPUT.getMessage());
        }
        return new FixedAmountVoucher(voucherId, value, localDate);
    }, FixedAmountVoucher.class::isInstance),
    PERCENT_DISCOUNT(2, (UUID voucherId, Long value, LocalDate localDate) -> {
        if (value < 0 || value > 100) {
            throw new IllegalArgumentException(ErrorMsg.WRONG_PERCENT_DISCOUNT_VALUE_INPUT.getMessage());
        }
        return new PercentDiscountVoucher(voucherId, value, localDate);
    }, PercentDiscountVoucher.class::isInstance),
    ERROR(99, (UUID voucherId, Long value, LocalDate localDate) -> {
        throw new IllegalArgumentException(ErrorMsg.WRONG_VOUCHER_TYPE_NUMBER.getMessage());
    }, Objects::nonNull);

    private final int number;
    private final TriFunction<UUID, Long, LocalDate, Voucher> triFunction;
    private final Predicate<Voucher> predicate;

    VoucherType(int number, TriFunction<UUID, Long, LocalDate, Voucher> triFunction, Predicate<Voucher> predicate) {
        this.number = number;
        this.triFunction = triFunction;
        this.predicate = predicate;
    }

    private static VoucherType findByNumber(int number) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.number == number)
                .findAny()
                .orElse(ERROR);
    }

    public static Voucher of(int number, UUID voucherId, long value, LocalDate createdAt) {
        return findByNumber(number).triFunction.apply(voucherId, value, createdAt);
    }

    public static int predictVoucherType(Voucher voucher) {
        return Arrays.stream(VoucherType.values())
                .filter(v -> v.predicate.test(voucher))
                .findAny()
                .orElse(ERROR).number;
    }

    public static boolean predictVoucherTypeNumber(int number) {
        return Arrays.stream(VoucherType.values())
                .anyMatch(voucherType -> voucherType.number == number);
    }
}
