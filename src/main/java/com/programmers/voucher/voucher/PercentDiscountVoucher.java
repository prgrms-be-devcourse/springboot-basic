package com.programmers.voucher.voucher;


import java.util.Objects;
import java.util.UUID;

import static com.programmers.voucher.voucher.VoucherType.PercentDiscount;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final long percentage;
    private boolean isAssigned;

    PercentDiscountVoucher(UUID voucherId, long percentage) {
        this.voucherId = voucherId;
        this.percentage = percentage;
        this.isAssigned = false;
    }

    public PercentDiscountVoucher(UUID voucherId, long percentage, boolean isAssigned) {
        this.voucherId = voucherId;
        this.percentage = percentage;
        this.isAssigned = isAssigned;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percentage / 100);
    }

    @Override
    public String toString() {
        return "ID =" + voucherId + '\n' +
                "type = PercentDiscountVoucher" + '\n' +
                "percentage = " + percentage;
    }

    @Override
    public long getValue() {
        return percentage;
    }

    @Override
    public VoucherType getType() {
        return PercentDiscount;
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
        PercentDiscountVoucher that = (PercentDiscountVoucher) o;
        return percentage == that.percentage && Objects.equals(getVoucherId(), that.getVoucherId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVoucherId(), percentage);
    }
}
