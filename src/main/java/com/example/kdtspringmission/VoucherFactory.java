package com.example.kdtspringmission;

import java.text.MessageFormat;

public class VoucherFactory {

    public static Voucher create(String name) {
        if (name.equals("FixedAmountVoucher")) {
            return new FixedAmountVoucher();
        }

        if (name.equals("RateAmountVoucher")) {
            return new RateAmountVoucher();
        }

        throw new IllegalArgumentException(MessageFormat.format("No such voucher name = {0}", name));
    }

}
