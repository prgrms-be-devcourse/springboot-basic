package org.prgrms.deukyun.voucherapp.domain.voucher.domain;

import java.text.MessageFormat;
import java.util.UUID;

public class VoucherFactory {

    public static Voucher createVoucher(String type, long argument) {
        if ("fixed".equals(type)) {
            return new FixedAmountDiscountVoucher(argument);
        } else if ("percent".equals(type)) {
            return new PercentDiscountVoucher(argument);
        } else {
            throw new IllegalArgumentException(MessageFormat.format("No Such type {0}", type));
        }
    }

    public static Voucher createVoucher(String type, UUID id, long amount, long percent) {
        if ("fixed".equals(type)) {
            return new FixedAmountDiscountVoucher(id, amount);
        } else if ("percent".equals(type)) {
            return new PercentDiscountVoucher(id, percent);
        } else {
            throw new IllegalArgumentException(MessageFormat.format("No Such type {0}", type));
        }
    }
}
