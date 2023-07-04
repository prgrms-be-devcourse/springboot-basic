package com.programmers.springbootbasic.service;

import com.programmers.springbootbasic.domain.voucher.*;

import java.time.LocalDateTime;
import java.util.UUID;

public final class VoucherFactory {

    private VoucherFactory() {

    }

    public static Voucher of(String type, String name, Long minimumPriceCondition, LocalDateTime createdDate, LocalDateTime expirationDate, int discount) {
        VoucherType voucherType = VoucherType.from(type);
        VoucherDate voucherDate = getVoucherDate(createdDate, expirationDate);
        return switch (voucherType) {
            case PERCENT ->
                    new PercentDiscountVoucher(UUID.randomUUID(), name, minimumPriceCondition, voucherDate, discount);
            case FIX -> new FixedAmountVoucher(UUID.randomUUID(), name, minimumPriceCondition, voucherDate, discount);
        };
    }

    private static VoucherDate getVoucherDate(LocalDateTime createdDate, LocalDateTime expirationDate) {
        return new VoucherDate(createdDate, expirationDate);
    }
}
