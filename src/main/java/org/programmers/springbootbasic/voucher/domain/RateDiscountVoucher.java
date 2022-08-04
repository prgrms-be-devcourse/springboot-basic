package org.programmers.springbootbasic.voucher.domain;

import java.sql.Timestamp;
import java.util.UUID;

import static org.programmers.springbootbasic.voucher.domain.VoucherType.RATE;

public class RateDiscountVoucher extends AbstractVoucher {

    public RateDiscountVoucher(UUID id, int amount) {
        super(id, amount, RATE);
    }

    public RateDiscountVoucher(UUID id, int amount, Long memberId, Timestamp registeredAt) {
        super(id, amount, RATE, memberId, registeredAt);
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (100 - this.getAmount()) / 100;
    }
}
