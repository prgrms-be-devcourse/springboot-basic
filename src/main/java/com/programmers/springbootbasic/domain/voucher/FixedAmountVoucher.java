package com.programmers.springbootbasic.domain.voucher;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class FixedAmountVoucher implements Voucher {
    private final UUID id;
    private final long amount;

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "id=" + id +
                ", amount=" + amount +
                '}';
    }
}
