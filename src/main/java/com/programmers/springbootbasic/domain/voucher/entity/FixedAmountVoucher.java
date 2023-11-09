package com.programmers.springbootbasic.domain.voucher.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long value;
    private final LocalDate createdAt;

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - value;
    }

    @Override
    public String getInformation() {
        return String.format("""
                FixedAmountVoucher:
                Voucher ID: %s
                FixedAmount: %d
                Created At: %s
                """, voucherId.toString(), value, createdAt.toString());
    }

}
