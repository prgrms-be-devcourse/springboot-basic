package org.programmers.springbootbasic.voucher.domain;

import java.sql.Timestamp;
import java.util.UUID;

import static org.programmers.springbootbasic.voucher.domain.VoucherType.FIXED;

public class FixedDiscountVoucher extends AbstractVoucher {

    public FixedDiscountVoucher(UUID id, int amount) {
        super(id, amount, FIXED);
    }

    public FixedDiscountVoucher(UUID id, int amount, Long memberId, Timestamp registeredAt) {
        super(id, amount, FIXED, memberId, registeredAt);
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - this.getAmount();
    }
}
