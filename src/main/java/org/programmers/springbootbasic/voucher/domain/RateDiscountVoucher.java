package org.programmers.springbootbasic.voucher.domain;

import java.util.UUID;

import static org.programmers.springbootbasic.voucher.domain.VoucherType.RATE;

public class RateDiscountVoucher extends AbstractVoucher {

    public RateDiscountVoucher(UUID id, int amount) {
        super(id, amount, RATE);
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (100 - this.getAmount()) / 100;
    }
}
