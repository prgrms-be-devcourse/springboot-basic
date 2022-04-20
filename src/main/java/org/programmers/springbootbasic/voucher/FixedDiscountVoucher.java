package org.programmers.springbootbasic.voucher;

import java.util.UUID;

public class FixedDiscountVoucher extends AbstractVoucher {

    public FixedDiscountVoucher(UUID id, int amount) {
        super(id, amount, VoucherType.FIXED);
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - this.getAmount();
    }
}
