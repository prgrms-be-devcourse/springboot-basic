package com.programmers.springweekly.domain.voucher;

import com.programmers.springweekly.util.Validator;
import java.util.UUID;

public class VoucherFactory {

    public static Voucher createVoucher(VoucherType voucherType, String discount) {

        switch (voucherType) {
            case FIXED -> {
                Validator.fixedAmountValidate(discount);
                return new FixedAmountVoucher(UUID.randomUUID(), Long.parseLong(discount));
            }
            case PERCENT -> {
                Validator.percentValidate(discount);
                return new PercentDiscountVoucher(UUID.randomUUID(), Long.parseLong(discount));
            }
        }

        throw new IllegalArgumentException("There is no voucher menu.");
    }
}
