package org.prgrms.deukyun.voucherapp.domain.voucher.domain;

import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class VoucherFactory {
    public Voucher createVoucher(String type, long argument) {
        if ("fixed".equals(type)) {
            return new FixedAmountDiscountVoucher(argument);
        } else if ("percent".equals(type)) {
            return new PercentDiscountVoucher(argument);
        } else {
            throw new IllegalArgumentException(MessageFormat.format("No Such type {0}", type));
        }
    }
}
