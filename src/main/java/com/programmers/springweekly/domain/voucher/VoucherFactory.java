package com.programmers.springweekly.domain.voucher;

import com.programmers.springweekly.util.Validator;

public class VoucherFactory {

    public static Voucher createVoucher(VoucherType voucherType, String discount) {

        switch (voucherType) {
            case FIXED -> {
                Validator.fixedAmountValidate(discount);
                return new FixedAmountVoucher(Long.parseLong(discount));
            }
            case PERCENT -> {
                Validator.percentValidate(discount);
                return new PercentDiscountVoucher(Long.parseLong(discount));
            }
            default -> throw new IllegalArgumentException("Input: " + voucherType + "There is no voucher menu.");
        }
    }
}
