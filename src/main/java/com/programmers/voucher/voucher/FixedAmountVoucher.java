package com.programmers.voucher.voucher;

import java.util.Objects;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final long amount;
    private final UUID voucherId;

    FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    public long discount(long beforeDiscount) {
        long discountedAmount = beforeDiscount - amount;
        return (discountedAmount < 0) ? 0 : discountedAmount;
    }

    @Override
    public String toString() {
        return "type=FixedAmountVoucher" + '\n' +
                "amount=" + amount;
    }

    @Override
    public long getValue() {
        return amount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FixedAmountVoucher that = (FixedAmountVoucher) o;
        return amount == that.amount && Objects.equals(getVoucherId(), that.getVoucherId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, getVoucherId());
    }
}
