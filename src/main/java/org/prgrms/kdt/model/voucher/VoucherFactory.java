package org.prgrms.kdt.model.voucher;

import java.util.UUID;

public class VoucherFactory {

    public static Voucher create(UUID voucherId, long value, VoucherType voucherType) {
        Voucher voucher = null;

        if (voucherType == VoucherType.FIXED_AMOUNT) {
            voucher = new FixedAmountVoucher(voucherId, value);
        } else if (voucherType == VoucherType.PERCENT_DISCOUNT) {
            voucher = new PercentDiscountVoucher(voucherId, value);
        }

        return voucher;
    }

    public static Voucher create(UUID voucherId, long value, String classSimpleName) {
        Voucher voucher = null;

        if (FixedAmountVoucher.class.getSimpleName().equals(classSimpleName)) {
            voucher = new FixedAmountVoucher(voucherId, value);
        } else if (PercentDiscountVoucher.class.getSimpleName().equals(classSimpleName)) {
            voucher = new PercentDiscountVoucher(voucherId, value);
        }

        return voucher;
    }
}
