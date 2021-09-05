package org.prgrms.kdt.voucher.model;

import org.springframework.stereotype.Component;

import java.util.UUID;

public class VoucherGenerator {

    public static Voucher createVoucher(UUID voucherId, long amount, String voucherType){
        return switch (VoucherType.getVoucherType(voucherType)) {
            case FIXED_AMOUNT_VOUCHER -> new FixedAmountVoucher(voucherId, amount);
            case PERCENT_DISCOUNT_VOUCHER -> new PercentDiscountVoucher(voucherId, amount);
        };
    }
}
