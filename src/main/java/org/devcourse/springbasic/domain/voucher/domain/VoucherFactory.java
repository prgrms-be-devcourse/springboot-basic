package org.devcourse.springbasic.domain.voucher.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherFactory {

    public static Voucher createVoucher(
            VoucherType voucherType,
            Long discountRate
    ) {
        switch (voucherType) {
            case FIXED_AMOUNT:
                return new FixedAmountVoucher(discountRate);
            case PERCENT_DISCOUNT:
                return new PercentDiscountVoucher(discountRate);
            default:
                throw new IllegalArgumentException("유효하지 않는 유형의 바우처");
        }
    }

    public static Voucher createVoucherWithParam(
            UUID voucherId,
            long discountRate,
            VoucherType voucherType,
            LocalDateTime createdAt
    ) {

        switch (voucherType) {
            case FIXED_AMOUNT:
                return new FixedAmountVoucher(voucherId, discountRate, createdAt);
            case PERCENT_DISCOUNT:
                return new PercentDiscountVoucher(voucherId, discountRate, createdAt);
            default:
                throw new IllegalArgumentException("유효하지 않는 유형의 바우처");
        }
    }
}
