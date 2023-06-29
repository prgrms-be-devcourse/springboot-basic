package com.programmers.springbootbasic.service;

import com.programmers.springbootbasic.domain.voucher.VoucherType;
import com.programmers.springbootbasic.domain.voucher.FixedAmountVoucher;
import com.programmers.springbootbasic.domain.voucher.PercentDiscountVoucher;
import com.programmers.springbootbasic.domain.voucher.Voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherFactory {

    private VoucherFactory() {

    }

    public static Voucher from(VoucherType voucherType, String name, Long minimumPriceCondition, LocalDateTime createdDate, LocalDateTime expirationDate, int discount) {
        switch (voucherType) {
            case RATE -> {
                if (minimumPriceCondition == null || minimumPriceCondition == 0) {
                    return new PercentDiscountVoucher(UUID.randomUUID(), name, createdDate, expirationDate, discount);
                }
                return new PercentDiscountVoucher(UUID.randomUUID(), name, minimumPriceCondition, createdDate, expirationDate, discount);
            }
            case FIX -> {
                if (minimumPriceCondition == null || minimumPriceCondition == 0) {
                    return new FixedAmountVoucher(UUID.randomUUID(), name, createdDate, expirationDate, discount);
                }
                return new FixedAmountVoucher(UUID.randomUUID(), name, minimumPriceCondition, createdDate, expirationDate, discount);
            }
            default -> throw new IllegalArgumentException("잘못된 쿠폰 종류 입력");
        }
    }
}
