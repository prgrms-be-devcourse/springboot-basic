package com.programmers.springbootbasic.domain.voucher.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class PercentDiscountVoucher implements Voucher {
    @Getter
    private final UUID voucherId;
    private final long value;

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (value / 100);
    }

    @Override
    public String getInformation() {
        return String.format("""
                PercentDiscountVoucher:
                Voucher ID: %s
                Discount Percent: %d%%
                """, voucherId.toString(), value);
    }
}
