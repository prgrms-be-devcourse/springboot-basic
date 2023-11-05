package com.prgrms.voucher_manage.domain.voucher.entity;

import java.util.UUID;

import static com.prgrms.voucher_manage.domain.voucher.entity.VoucherType.FIXED;

public class FixedAmountVoucher extends Voucher {
    private static final Long MIN_DISCOUNT_PRICE = 0L;

    public FixedAmountVoucher(Long discountPrice) {
        super(UUID.randomUUID(), discountPrice, FIXED);
    }

    public FixedAmountVoucher(UUID voucherId, Long discountPrice) {
        super(voucherId, discountPrice, FIXED);
    }

    public static boolean isInvalidPrice(Long discountAmount) {
        return discountAmount < MIN_DISCOUNT_PRICE;
    }
}
