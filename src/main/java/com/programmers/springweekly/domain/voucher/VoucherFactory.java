package com.programmers.springweekly.domain.voucher;

import com.programmers.springweekly.util.Validator;

import java.util.UUID;

public class VoucherFactory {

    public static Voucher createVoucher(UUID voucherId, VoucherType voucherType, String discount) {

        switch (voucherType) {
            case FIXED -> {
                Validator.fixedAmountValidate(discount);
                return new FixedAmountVoucher(voucherId, Long.parseLong(discount));
            }
            case PERCENT -> {
                Validator.percentValidate(discount);
                return new PercentDiscountVoucher(voucherId, Long.parseLong(discount));
            }
            default -> throw new IllegalArgumentException("Input: " + voucherType + "바우처 타입이 없습니다.");
        }
    }
}
