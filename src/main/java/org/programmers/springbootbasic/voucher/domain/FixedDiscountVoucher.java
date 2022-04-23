package org.programmers.springbootbasic.voucher.domain;

import java.util.UUID;

public class FixedDiscountVoucher extends AbstractVoucher {

    public FixedDiscountVoucher(UUID id, int amount) {
        super(id, amount, VoucherType.FIXED);
    }

    public FixedDiscountVoucher(UUID id, int amount, Long memberId) {
        super(id, amount, VoucherType.FIXED, memberId);
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - this.getAmount();
    }
}
