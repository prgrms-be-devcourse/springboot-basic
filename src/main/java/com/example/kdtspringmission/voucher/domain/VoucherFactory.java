package com.example.kdtspringmission.voucher.domain;

import java.text.MessageFormat;

public class VoucherFactory {

    public static Voucher create(String name) {
        if (name.equals("1")) {
            return new FixedAmountVoucher();
        }

        if (name.equals("2")) {
            return new RateAmountVoucher();
        }
        throw new IllegalArgumentException(MessageFormat.format("No such voucher name = {0}", name));
    }
}
