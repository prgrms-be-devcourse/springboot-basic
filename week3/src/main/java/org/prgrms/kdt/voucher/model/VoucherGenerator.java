package org.prgrms.kdt.voucher.model;

import java.util.UUID;

public class VoucherGenerator {
    public static Voucher createVoucher(UUID voucherId, long amount, VoucherType voucherType){
        return switch (voucherType) {
            case FIXED_AMOUNT_VOUCHER -> new FixedAmountVoucher(voucherId, amount, voucherType);
            case PERCENT_DISCOUNT_VOUCHER -> new PercentDiscountVoucher(voucherId, amount, voucherType);
        };
    }
}
