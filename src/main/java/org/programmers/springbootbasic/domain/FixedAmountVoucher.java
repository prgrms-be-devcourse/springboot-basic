package org.programmers.springbootbasic.domain;

import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final long amount;

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public long discount(long originalPrice) {
        if(originalPrice - amount < 0) throw new ArithmeticException("할인된 금액은 0원보다 작을 수 없습니다.");
        return originalPrice - amount;
    }
}
