package com.programmers.springbootbasic.domain.voucher.service;

import com.programmers.springbootbasic.domain.voucher.entity.FixedAmountVoucher;
import com.programmers.springbootbasic.domain.voucher.entity.PercentDiscountVoucher;
import com.programmers.springbootbasic.domain.voucher.entity.Voucher;
import com.programmers.springbootbasic.domain.voucher.exception.ErrorMsg;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.BiFunction;

public enum VoucherType {
    FixedAmount(1, (UUID voucherId, Long value) -> {
        if (value < 0) {
            throw new IllegalArgumentException(ErrorMsg.WrongFixedAmountValueInput.getMessage());
        }
        return new FixedAmountVoucher(voucherId, value);
    }),
    PercentDiscount(2, (UUID voucherId, Long value) -> {
        if (value < 0 || value > 100) {
            throw new IllegalArgumentException(ErrorMsg.WrongPercentDiscountValueInput.getMessage());
        }
        return new PercentDiscountVoucher(voucherId, value);
    }),
    Error(99, (UUID voucherId, Long value) -> {
        throw new IllegalArgumentException(ErrorMsg.WrongVoucherTypeNumber.getMessage());
    });

    private final int number;
    private final BiFunction<UUID, Long, Voucher> biFunction;

    VoucherType(int number, BiFunction<UUID, Long, Voucher> biFunction) {
        this.number = number;
        this.biFunction = biFunction;
    }

    private static VoucherType findByNumber(int number) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.number == number)
                .findAny()
                .orElse(Error);
    }

    public static Voucher of(int number, UUID voucherId, long value) {
        return findByNumber(number).biFunction.apply(voucherId, value);
    }
}
