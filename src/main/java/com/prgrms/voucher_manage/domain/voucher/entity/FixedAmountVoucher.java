package com.prgrms.voucher_manage.domain.voucher.entity;

import static com.prgrms.voucher_manage.domain.voucher.entity.VoucherType.FIXED;

public class FixedAmountVoucher extends Voucher {
    private static final Long MIN_DISCOUNT_PRICE = 0L;
    public FixedAmountVoucher(Long discountPrice) {
        super(discountPrice, FIXED);
    }
    public static boolean isInvalidPrice(Long discountAmount) {
        return discountAmount < MIN_DISCOUNT_PRICE;
    }
}
