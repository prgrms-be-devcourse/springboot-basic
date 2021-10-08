package org.prgrms.kdt.voucher;

import java.util.UUID;

public class VoucherFactory {
    private VoucherFactory() {
    }

    public static Voucher create(final String voucherType, final long discountAmount) {
        if (voucherType.equals("FixedAmountVoucher")) {
            return new FixedAmountVoucher(UUID.randomUUID(), discountAmount);
        } else if (voucherType.equals("PercentDiscountVoucher")) {
            return new PercentDiscountVoucher(UUID.randomUUID(), discountAmount);
        } else {
            throw new IllegalArgumentException("잘못된 Voucher Type 입니다.");
        }
    }

    public static Voucher create(final String voucherType, final UUID voucherId, final long discountAmount) {
        if (voucherType.equals("FixedAmountVoucher")) {
            return new FixedAmountVoucher(voucherId, discountAmount);
        } else if (voucherType.equals("PercentDiscountVoucher")) {
            return new PercentDiscountVoucher(voucherId, discountAmount);
        } else {
            throw new IllegalArgumentException("잘못된 Voucher Type 입니다.");
        }
    }
}
