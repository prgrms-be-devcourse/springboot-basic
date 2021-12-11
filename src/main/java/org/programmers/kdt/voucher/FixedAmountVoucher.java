package org.programmers.kdt.voucher;

import java.util.Objects;
import java.util.UUID;


public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long amount;

    private VoucherStatus status;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
        status = VoucherStatus.VALID;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getDiscount() {
        return amount;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.FIXED;
    }

    @Override
    public long getDiscountedPrice(long beforeDiscount) {
        long discounted = beforeDiscount - amount;
        return discounted >= 0 ? discounted : 0;
    }

    @Override
    public VoucherStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FixedAmountVoucher that = (FixedAmountVoucher) o;
        return voucherId.equals(that.voucherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherId);
    }
}
