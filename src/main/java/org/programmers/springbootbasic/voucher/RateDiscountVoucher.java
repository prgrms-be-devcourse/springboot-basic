package org.programmers.springbootbasic.voucher;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class RateDiscountVoucher implements Voucher {

    private final UUID id;
    private final long percent;

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (100-percent) / 100;
    }

    @Override
    public UUID getId() {
        return id;
    }

}
