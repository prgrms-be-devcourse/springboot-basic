package org.prgms.w3d1.model.voucher;

import java.util.UUID;

public class VoucherFactory {

    public static Voucher getVoucher(UUID voucherId, long value, String type, UUID voucherWalletId){
        VoucherType voucherType = VoucherType.getVoucherType(type);
        switch (voucherType) {
            case FIXED_AMOUNT_VOUCHER -> {
                return new FixedAmountVoucher(voucherId, value, voucherWalletId);
            }
            case PERCENT_DISCOUNT_VOUCHER -> {
                return new PercentDiscountVoucher(voucherId, value, voucherWalletId);
            }
            default -> throw new IllegalArgumentException("Wrong voucher Type");

        }
    }
}
