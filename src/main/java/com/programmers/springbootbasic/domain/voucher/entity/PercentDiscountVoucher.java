package com.programmers.springbootbasic.domain.voucher.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final long value;
    private final LocalDate createdAt;

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
                Created At: %s
                """, voucherId.toString(), value, createdAt.toString());
    }
}
