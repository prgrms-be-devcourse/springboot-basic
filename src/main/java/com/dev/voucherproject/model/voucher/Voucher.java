package com.dev.voucherproject.model.voucher;

import java.util.Objects;
import java.util.UUID;

public abstract class Voucher {
    private UUID voucherId;

    public Voucher(UUID voucherId) {
        this.voucherId = voucherId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public abstract long discount(long beforeDiscount);

    public abstract long getDiscountFigure();

    public abstract VoucherPolicy getPolicyName();

    public static Voucher of(UUID uuid, VoucherPolicy voucherPolicy, long discountFigure) {
        if (voucherPolicy == VoucherPolicy.FIXED_AMOUNT_VOUCHER) {
            return new FixedAmountVoucher(uuid, discountFigure);
        }

        return new PercentDiscountVoucher(uuid, discountFigure);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voucher voucher = (Voucher) o;
        return Objects.equals(voucherId, voucher.voucherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherId);
    }
}
