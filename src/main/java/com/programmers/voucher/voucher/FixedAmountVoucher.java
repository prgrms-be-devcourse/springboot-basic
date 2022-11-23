package com.programmers.voucher.voucher;

import java.util.Objects;
import java.util.UUID;

import static com.programmers.voucher.voucher.VoucherType.FixedAmount;

public class FixedAmountVoucher implements Voucher {
    private final long amount;
    private final UUID voucherId;
    private boolean isAssigned;

    FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.isAssigned = false;
    }

    public FixedAmountVoucher(long amount, UUID voucherId, boolean isAssigned) {
        this.amount = amount;
        this.voucherId = voucherId;
        this.isAssigned = isAssigned;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    public long discount(long beforeDiscount) {
        long discountedAmount = beforeDiscount - amount;
        return Math.max(discountedAmount, 0);
    }

    @Override
    public String toString() {
        return "ID =" + voucherId + '\n' +
                "type = FixedAmountVoucher" + '\n' +
                "amount = " + amount;
    }

    @Override
    public long getValue() {
        return amount;
    }

    @Override
    public VoucherType getType() {
        return FixedAmount;
    }

    @Override
    public void changeAssigned(boolean isAssigned) {
        this.isAssigned = isAssigned;
    }

    @Override
    public boolean isAssigned() {
        return isAssigned;
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
