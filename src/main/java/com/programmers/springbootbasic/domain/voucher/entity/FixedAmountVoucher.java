package com.programmers.springbootbasic.domain.voucher.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long value;

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
                """, voucherId.toString(), value);
    }

}
