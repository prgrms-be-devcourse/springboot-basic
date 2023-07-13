package com.programmers.springweekly.domain.voucher;

import com.programmers.springweekly.util.Validator;

import java.util.UUID;

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
            default -> throw new IllegalArgumentException("Input: " + voucherType + "바우처 타입이 없습니다.");
        }
    }

    public static Voucher createVoucherOfFile(UUID uuid, VoucherType voucherType, String discount) {

        switch (voucherType) {
            case FIXED -> {
                Validator.fixedAmountValidate(discount);
                return new FixedAmountVoucher(uuid, Long.parseLong(discount));
            }
            case PERCENT -> {
                Validator.percentValidate(discount);
                return new PercentDiscountVoucher(uuid, Long.parseLong(discount));
            }
            default -> throw new IllegalArgumentException("Input: " + voucherType + "바우처 타입이 없습니다.");
        }
    }
}
