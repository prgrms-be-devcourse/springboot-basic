package com.program.commandLine.model.voucher;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherFactory {
    public Voucher createVoucher(VoucherType voucherType, UUID voucherId, int discount) {

        Voucher newVoucher = switch (voucherType) {
            case PERCENT_DISCOUNT -> new PercentDiscountVoucher(voucherId, discount);
            case FIXED_AMOUNT_DISCOUNT -> new FixedAmountVoucher(voucherId, discount);
        };
        return newVoucher;
    }

    public Voucher createVoucher(VoucherType voucherType, UUID voucherId, int discount, boolean used) {

        Voucher newVoucher = switch (voucherType) {
            case PERCENT_DISCOUNT -> new PercentDiscountVoucher(voucherId, discount, used);
            case FIXED_AMOUNT_DISCOUNT -> new FixedAmountVoucher(voucherId, discount, used);
        };
        return newVoucher;
    }
}
