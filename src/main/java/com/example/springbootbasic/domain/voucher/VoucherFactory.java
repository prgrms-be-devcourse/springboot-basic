package com.example.springbootbasic.domain.voucher;

import java.time.LocalDateTime;

import static com.example.springbootbasic.domain.voucher.VoucherType.FIXED_AMOUNT;
import static com.example.springbootbasic.domain.voucher.VoucherType.PERCENT_DISCOUNT;

public class VoucherFactory {

    private VoucherFactory(){
    }

    public static Voucher of(long voucherId,
                             long discountValue,
                             VoucherType voucherType,
                             LocalDateTime createdAt,
                             LocalDateTime startAt,
                             LocalDateTime endAt
    ) {
        if (voucherType == FIXED_AMOUNT) {
            return new FixedAmountVoucher(voucherId, discountValue, createdAt, startAt, endAt);
        }
        if (voucherType == PERCENT_DISCOUNT) {
            return new PercentDiscountVoucher(voucherId, discountValue, createdAt, startAt, endAt);
        }
        throw new IllegalArgumentException();
    }

    public static Voucher of(long discountValue,
                             VoucherType voucherType,
                             LocalDateTime createdAt,
                             LocalDateTime startAt,
                             LocalDateTime endAt
    ) {
        if (voucherType == FIXED_AMOUNT) {
            return new FixedAmountVoucher(discountValue, createdAt, startAt, endAt);
        }
        if (voucherType == PERCENT_DISCOUNT) {
            return new PercentDiscountVoucher(discountValue, createdAt, startAt, endAt);
        }
        throw new IllegalArgumentException();
    }
}
