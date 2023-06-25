package com.programmers.springweekly.domain.voucher;

import com.programmers.springweekly.util.Validator;

import java.util.UUID;

public class VoucherFactory {

    public Voucher createVoucher(VoucherMenu voucherMenu, String discount){

        switch (voucherMenu){
            case FIXED -> {
                Validator.fixedAmountValidate(discount);
                return new FixedAmountVoucher(UUID.randomUUID(), Long.parseLong(discount), "fixed");
            }
            case PERCENT -> {
                Validator.percentValidate(discount);
                return new PercentDiscountVoucher(UUID.randomUUID(), Long.parseLong(discount), "percent");
            }
        }

        throw new IllegalArgumentException("There is no voucher menu.");
    }
}
