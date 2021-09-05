package org.prgms.w3d1.model.voucher;

import java.util.UUID;

public class VoucherFactory {

    public static Voucher getVoucher(UUID voucherId, long value, String type){
        VoucherType voucherType = VoucherType.getCommand(type);
        switch (voucherType) {
            case FIXED_AMOUNT_VOUCHER -> {
                return FixedAmountVoucher.of(voucherId, value);
            }
            case PERCENT_DISCOUNT_VOUCHER -> {
                return PercentDiscountVoucher.of(voucherId, value);
            }
            default -> throw new IllegalArgumentException("Wrong voucher Type");

        }
    }
}
