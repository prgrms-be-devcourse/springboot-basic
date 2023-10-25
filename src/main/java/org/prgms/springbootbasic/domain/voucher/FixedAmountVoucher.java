package org.prgms.springbootbasic.domain.voucher;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import static java.lang.Math.max;

@Slf4j
public class FixedAmountVoucher implements VoucherPolicy {
    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return this.voucherId;
    }

    @Override
    public long getDiscountAmount() {
        return this.amount;
    }

    @Override
    public long discount(long beforeDiscount) {
        return max(0, beforeDiscount - this.amount);
    }
}
